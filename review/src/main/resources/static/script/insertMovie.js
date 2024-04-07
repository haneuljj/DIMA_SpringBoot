document.getElementById("insertBtn").addEventListener('click', function(){
    let movieName = document.getElementById('movieName').value;
    let movieSummary = document.getElementById('movieSummary').value;

    if(movieName.trim().length == 0 || movieSummary.trim().length == 0) {
        alert('제목 또는 내용이 비어있습니다.');
        event.preventDefault();
        return;
    }
})