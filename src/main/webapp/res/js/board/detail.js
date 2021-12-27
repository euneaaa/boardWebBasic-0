function isDelCmt(iboard, icmt) {

    if(confirm('댓글을 삭제하시겠습니까?')){
        location.href = '/board/cmt/del?iboard='+iboard+'&icmt='+icmt;
    }
}

var cmtModCotainerElem = document.querySelector('.cmtModContainer');
var btnCancelElem = cmtModCotainerElem.querySelector('#btnCancel');
btnCancelElem.addEventListener('click', function (){
    cmtModCotainerElem.style.display='none';
})

function openModForm(icmt, ctnt){
        cmtModCotainerElem.style.display='flex';

        var cmtModFrmElem = cmtModCotainerElem.querySelector('#cmtModFrm');
        cmtModFrmElem.icmt.value = icmt;
        cmtModFrmElem.ctnt.value = ctnt;

}