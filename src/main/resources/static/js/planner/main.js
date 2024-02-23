// 메뉴창-모달
const modal = document.querySelector(".modal");
const btnOpenModal = document.querySelector(".menu-button");

btnOpenModal.addEventListener("click", () => {
    modal.style.display = "flex";
});

//메뉴-모달창: 어두운 부분 클릭하면 메뉴창 없어짐
const modalBody = document.querySelector(".modal-body");
modal.addEventListener("click", function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
});
modalBody.addEventListener("click", function (event) {
    event.stopPropagation();
});


// 게시글 올리기-모달
const modalUploadPost = document.querySelector(".modal-upload-post");
const btnOpenModalUploadPost = document.querySelector("#upload-post");
const btnCancelModalUploadPost = document.querySelector(".cancel-btn");
btnOpenModalUploadPost.addEventListener("click", () => {
    modalUploadPost.style.display = "flex";
});
//취소 버튼 누르면
btnCancelModalUploadPost.addEventListener("click", () => {
    modalUploadPost.style.display = "none";
});



// 시간 측정 버튼
document.addEventListener("DOMContentLoaded", function () {
    const startButton = document.querySelector(".start-button");
    const stopButton = document.querySelector(".stop-button");

    startButton.addEventListener("click", function () {
        startButton.style.display = "none";
        stopButton.style.display = "block";
    });

    stopButton.addEventListener("click", function () {
        stopButton.style.display = "none";
        startButton.style.display = "block";
    });
});

//Home 버튼을 클릭했을 때
function redirectToHome() {
    var userId = 1;
    window.location.href = "/planners/home/" + userId;
}

//Board 버튼을 클릭했을 때
function redirectToBoard() {
    window.location.href = "/posts/all";
}


