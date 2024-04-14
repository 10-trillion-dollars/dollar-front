// common.js

function getTokenFromCookie() {
  const cookies = document.cookie.split(';');
  for (let cookie of cookies) {
    const parts = cookie.split('=');
    const name = parts[0].trim();
    if (name === 'Authorization') {
      return parts[1];
    }
  }
  return null;
}

document.addEventListener('DOMContentLoaded', function() {
  fetch('/components/header.html')
  .then(response => response.text())
  .then(html => {
    document.getElementById('nav-container').innerHTML = html;
    bindHeaderEvents(); // header.html의 스크립트를 바인딩하는 함수 호출
  });
});

// 장바구니 이동 함수
function bindHeaderEvents() {
  // header.html에서 필요한 모든 이벤트 리스너를 여기에 바인딩

  // 장바구니 버튼의 이벤트 리스너
  document.getElementById('basket').addEventListener('click', function() {
    window.location.href = '/cart.html';
  });

  // 검색 이벤트 리스너 바인딩
  const searchButton = document.getElementById('searchBtn');
  const searchBox = document.getElementById('searchBox');

  if (searchButton && searchBox) {
    searchButton.addEventListener('click', () => {
      const searchQuery = searchBox.value;
      fetchSearchProducts(searchQuery); // 검색 함수를 호출합니다.
    });
  }

  // 토큰 체크
  checkToken();
}

// 검색 함수
function fetchSearchProducts(query) {
  fetch(`http://localhost:8080/products/search?search=${query}`)
  .then(response => response.json())
  .then(products => displayProducts(products))
  .catch(error => console.error('Error:', error));
}

// 로그인 로그아웃 토큰 체크
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

// 장바구니 함수
function addToCart(product, quantity) {
  let cart = JSON.parse(localStorage.getItem('cart')) || [];
  const productIndex = cart.findIndex(item => item.productId === product.id);

  if (productIndex !== -1) {
    // 상품이 이미 있으면, 수량만 업데이트
    cart[productIndex].quantity += parseInt(quantity, 10);
  } else {
    // 상품이 장바구니에 없으면, 상품 정보와 함께 추가
    cart.push({
      productId: product.id,
      name: product.name,
      price: product.price,
      quantity: parseInt(quantity, 10),
      photo: product.photo
    });
  }

  // 변경된 장바구니 데이터를 로컬 스토리지에 저장
  localStorage.setItem('cart', JSON.stringify(cart));
  alert('장바구니에 상품이 추가되었습니다!');
}
