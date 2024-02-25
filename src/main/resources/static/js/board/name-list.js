//Home 버튼을 클릭했을 때
function redirectToHome() {
    var userId = 1;
    window.location.href = "/planners/home/" + userId;
}

//Board 버튼을 클릭했을 때
function redirectToBoard() {
    window.location.href = "/posts/all";
}

//버튼 색 자동
const buttonColors = ['#FEE1E8', '#FED7C3', '#A2E1DB', '#F6EAC2', '#CED986', '#9DB5D8', '#D0B8DE', '#C6DFE2'];
const buttons = document.querySelectorAll('.board');

buttons.forEach((button, index) => {
    const color = index < buttonColors.length ? buttonColors[index] : getRandomColor();
    button.style.backgroundColor = color;
});

function getRandomColor() {
    return '#' + Math.floor(Math.random()*16777215).toString(16);
}

// 게시판 생성-모달
const modalCreateBoard = document.querySelector(".modal-create");
const btnOpenModalUploadPost = document.querySelector("#createBoard");
const btnCancelModalUploadPost = document.querySelector(".cancel-btn");
btnOpenModalUploadPost.addEventListener("click", () => {
    modalCreateBoard.style.display = "flex";
});
//취소 버튼 누르면
btnCancelModalUploadPost.addEventListener("click", () => {
    modalCreateBoard.style.display = "none";
});