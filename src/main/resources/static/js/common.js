// common.js
document.addEventListener('DOMContentLoaded', function() {
  fetch('/components/header.html')
  .then(response => response.text())
  .then(html => {
    document.getElementById('nav-container').innerHTML = html;
    bindHeaderEvents(); // header.html의 스크립트를 바인딩하는 함수 호출
  });
});


//장바구니 이동 함수
function bindHeaderEvents() {
  // header.html에서 필요한 모든 이벤트 리스너를 여기에 바인딩
  // 예: 장바구니 버튼의 이벤트 리스너
  document.getElementById('basket').addEventListener('click', function() {
    window.location.href = '/cart.html';
  });
  //검색 이벤트 리스너 바인딩
  const searchButton = document.getElementById('searchBtn');
  const searchBox = document.getElementById('searchBox');

  if (searchButton && searchBox) {
    searchButton.addEventListener('click', () => {
      const searchQuery = searchBox.value;
      fetchSearchProducts(searchQuery); // 검색 함수를 호출합니다.
    });
  }
  //토큰체크
  checkToken();
}

//검색 함수
function fetchSearchProducts(query) {
  fetch(`http://localhost:8080/products/search?search=${query}`)
  .then(response => response.json())
  .then(products => displayProducts(products))
  .catch(error => console.error('Error:', error));
}
//로그인 로그아웃 토큰체크
function checkToken() {
  const token = getTokenFromCookie();
  const authButtons = document.getElementById('authButtons');

  if (token) {
    // 토큰이 있을 경우
    authButtons.innerHTML = `
    <button id="orderPage">주문내역</button>
    <button id="logout">로그아웃</button>
    `;
    // 마이페이지 이벤트 리스너
    document.getElementById('myPage').addEventListener('click', function() {
      window.location.href = 'myPage.html';
    });
    // 주문내역 페이지 이벤트 리스너
    document.getElementById('orderPage').addEventListener('click', function() {
      window.location.href = 'orderComplete.html';
    });
    // 로그아웃 버튼 이벤트 리스너
    document.getElementById('logout').addEventListener('click', function() {
      document.cookie = 'Authorization=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      alert('로그아웃 되었습니다.');
      window.location.reload();
    });
  } else {
    // 토큰이 없을 경우
    authButtons.innerHTML = `
    <button id="loginSignupBtn">로그인/회원가입</button>
    `;
    // 로그인/회원가입 페이지 이벤트 리스너
    document.getElementById('loginSignupBtn').addEventListener('click', function() {
      window.location.href = 'loginSignup.html';
    });
  }
}
