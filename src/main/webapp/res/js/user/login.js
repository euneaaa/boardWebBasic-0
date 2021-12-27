var frm = document.querySelector('#frm');
console.log('frm : ' + frm);
if(frm) {
    function frmSubmitEvent(e) {
        if(frm.uid.value.length < 5 || frm.uid.value.length > 20) {
            // 아이디가 5글자 이만 혹은 20글자 초과가 되면 "아이디는 5~20글자 입니다." No send
            alert('아이디는 5~20글자 입니다.');
            e.preventDefault();
            return;

        } else if(frm.upw.value.length < 5) {
            // 비밀번호는 5글자 미만 일 때 "비밀번호를 확인해 주세요." No send
            alert('비밀번호를 확인해 주세요.');
            e.preventDefault();
            return;
        }
    }
    frm.addEventListener('submit', frmSubmitEvent);

    var btnShowPw = document.querySelector('#btnShowPw');
    if(btnShowPw) {
        btnShowPw.addEventListener('click', function() {
            if(frm.upw.type === 'password') {
                frm.upw.type = 'text';
                btnShowPw.value = '비밀번호 숨기기';
            } else {
                frm.upw.type = 'password';
                btnShowPw.value = '비밀번호 보이기';
            }
        });
    }
}














