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
    return '#' + Math.floor(Math.random() * 16777215).toString(16);
}

//////////모달창///////////////
//수정 모달창 열기
const modalModifyBoard = document.querySelector(".modal-modify");
const modifyButtons = document.querySelectorAll(".modifyBoard");
//const btnCancelModal = document.querySelector(".cancel-btn");
const btnCancelModalModify = document.querySelector(".modal-modify .cancel-btn");

modifyButtons.forEach(button => {
    button.addEventListener("click", () => {
        const boardId = button.getAttribute("data-boardid");
        document.getElementById("modifyForm").action = "/boards/" + boardId;
        modalModifyBoard.style.display = "flex";
    });
});
// 취소 버튼 누른 경우
btnCancelModalModify.addEventListener("click", () => {
    modalModifyBoard.style.display = "none";
});


//삭제 모달창 열기
const modalDeleteBoard = document.querySelector(".modal-delete");
const deleteButtons = document.querySelectorAll(".deleteBoard");
const btnCancelModalDelete = document.querySelector(".modal-delete .cancel-btn");

deleteButtons.forEach(button => {
    button.addEventListener("click", () => {
        const boardId = button.getAttribute("data-boardid");
        document.getElementById("deleteForm").action = "/boards/delete/" + boardId;
        modalDeleteBoard.style.display = "flex";
    });
});
// 취소 버튼 누른 경우
btnCancelModalDelete.addEventListener("click", () => {
    modalDeleteBoard.style.display = "none";
});