//Home 버튼을 클릭했을 때
function redirectToHome() {
    var userId = 1;
    window.location.href = "/planners/home/" + userId;
}

//Board 버튼을 클릭했을 때
function redirectToBoard() {
    window.location.href = "/posts/all";
}

//////////모달창///////////////
//수정 모달창 열기
const modalModifyBoard = document.querySelector(".modal-modify");
const modifyButtons = document.querySelectorAll(".modifyBoard");
//const btnCancelModal = document.querySelector(".cancel-btn");
const btnCancelModalModify = document.querySelector(".modal-modify .cancel-btn");

modifyButtons.forEach(button => {
    button.addEventListener("click", () => {
        const postId = button.getAttribute("data-boardid");
        document.getElementById("modifyForm").action = "/posts/" + postId;
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
        const postId = button.getAttribute("data-boardid");
        document.getElementById("deleteForm").action = "/posts/delete/" + postId;
        modalDeleteBoard.style.display = "flex";
    });
});
// 취소 버튼 누른 경우
btnCancelModalDelete.addEventListener("click", () => {
    modalDeleteBoard.style.display = "none";
});

////////// 댓글창 //////////////
const modal = document.querySelector(".modal");
const btnOpenModal = document.querySelector(".comment-button");
const btnCancel = document.querySelector(
    ".modal-body .close-wrapper .material-symbols-outlined"
);

btnOpenModal.addEventListener("click", () => {
    modal.style.display = "flex";
});

// close 버튼 누른 경우
btnCancel.addEventListener("click", () => {
    modal.style.display = "none";
});

//댓글 수정 모달창 열기/////
const modalModifyComment = document.querySelector(".modal-modify-comment");
const modifyCommentButtons = document.querySelectorAll(".modifyComment");
const btnCancelModalModifyComment = document.querySelector(".modal-modify-comment .cancel-btn");

modifyCommentButtons.forEach(button => {
    button.addEventListener("click", () => {
        const commentId = button.getAttribute("data-commentId");
        document.getElementById("modifyForm-comment").action = "/comments/" + commentId;
        modalModifyComment.style.display = "flex";
    });
});
// 취소 버튼 누른 경우
btnCancelModalModifyComment.addEventListener("click", () => {
    modalModifyComment.style.display = "none";
});


//댓글 삭제 모달창 열기/////
const modalDeleteComment = document.querySelector(".modal-delete-comment");
const deleteCommentButtons = document.querySelectorAll(".deleteComment");
const btnCancelModalDeleteComment = document.querySelector(".modal-delete-comment .cancel-btn");

deleteCommentButtons.forEach(button => {
    button.addEventListener("click", () => {
        const commentId = button.getAttribute("data-commentId");
        document.getElementById("deleteForm-comment").action = "/comments/delete/" + commentId;
        modalDeleteComment.style.display = "flex";
    });
});
// 취소 버튼 누른 경우
btnCancelModalDeleteComment.addEventListener("click", () => {
    modalDeleteComment.style.display = "none";
});