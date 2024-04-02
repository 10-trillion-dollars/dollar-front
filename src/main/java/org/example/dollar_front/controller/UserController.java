package org.example.dollar_front.controller;

import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.dollar_front.dto.LoginForm;
import org.example.dollar_front.dto.SignupForm;
import org.example.dollar_front.feign.UserServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceClient userServiceClient;

    @GetMapping("/loginSignup")
    public String showLoginSignupPage() {
        return "loginSignup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupForm form, RedirectAttributes redirectAttributes) {
        Map<String, Object> signupData = new HashMap<>();
        signupData.put("email", form.getEmail());
        signupData.put("username", form.getUsername());
        signupData.put("password", form.getPassword());
        signupData.put("admin", form.isAdmin());
        signupData.put("adminToken", form.getAdminToken());

        try {
            String response = userServiceClient.signup(signupData);
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            // return "redirect:/loginSignup";
        } catch (FeignException e) {
            String errorBody = e.contentUTF8();
            // 오류 응답 바디를 로깅하거나 분석
            System.out.println("Error response body: " + errorBody);
            redirectAttributes.addFlashAttribute("message", "회원가입에 실패하였습니다.");
            return "redirect:/loginSignup";
        }
        return "로그인 성공";
    }

    @PostMapping("/login")
    public String login(
        @ModelAttribute LoginForm form,
        RedirectAttributes redirectAttributes,
        HttpServletResponse httpServletResponse
    ) {
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("email", form.getEmail());
        loginData.put("password", form.getPassword());

        try {
            ResponseEntity<String> responseEntity = userServiceClient.login(loginData);
            List<String> authHeaders = responseEntity.getHeaders().get("Authorization");
            if (authHeaders != null && !authHeaders.isEmpty()) {
                String authToken = authHeaders.get(0);
                String encodedToken = authToken.replace(" ", "%20");

                Cookie cookie = new Cookie("Authorization", encodedToken);
                httpServletResponse.addCookie(cookie);
            }
            redirectAttributes.addFlashAttribute("message", "로그인이 완료되었습니다.");
            return "redirect:/"; // 홈 페이지로 리다이렉트
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "로그인에 실패하였습니다.");
            return "redirect:/loginSignup"; // 로그인 페이지로 리다이렉트
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // 쿠키에서 토큰 제거
        Cookie cookie = new Cookie("Authorization", null); // 쿠키 이름을 AuthToken으로 가정
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키의 유효 기간을 0으로 설정하여 즉시 만료시킵니다.
        cookie.setHttpOnly(true); // JavaScript를 통한 접근 방지
        response.addCookie(cookie);

        return "redirect:/"; // 메인 페이지로 리다이렉트
    }
}
