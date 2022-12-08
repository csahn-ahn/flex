// 일자 포맷
// format : YYYY.MM.DD HH:mm
function convertDateFormat(date, format) {
	if(date == null || date == '') {
		return '-';
	}
	return (moment(date)).format(format);
}

// 휴대폰번호 포맷 체크
function chkFormatPhone(phoneNo) {
	var regex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	var re = new RegExp(regex);
	return re.test(phoneNo);
}

// 이메일 포맷 체크
function chkFormatEmail(email) {
	var regex = /^[0-9a-zA-Z]([_]?([\w\.\%\+\-])*[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	return regex.test(email);
}

// 페이징 마크업 새엇ㅇ
function makePagination() {

}