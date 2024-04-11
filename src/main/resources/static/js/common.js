document.addEventListener('DOMContentLoaded', function() {
  fetch('/components/header.html')
  .then(response => response.text())
  .then(html => {
    document.getElementById('nav-container').innerHTML = html;
  });
});
