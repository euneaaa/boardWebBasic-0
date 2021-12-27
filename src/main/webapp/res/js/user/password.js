var frmElem = document.querySelector('#frm');
var submitBtnElem = document.querySelector('#submitBtn');

submitBtnElem.addEventListener('click',function (){
    if(frmElem.upw.value.length<5){
        alert('비밀번호가 5자 이하입니다.')
    }
    else if (frmElem.changedUpw.value.length<5 ){
        alert('변경할 비밀번호를 5자 이상으로 해주세요.')
    }else if (frmElem.changedUpw.value!==frm.changedUpwConfirm.value){
        alert('비밀번호가 서로 다릅니다.')
    }else {
        frmElem.submit();
    }
})