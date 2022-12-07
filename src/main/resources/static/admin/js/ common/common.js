// 일자 포맷
// format : YYYY.MM.DD HH:mm
function convertDateFormat(date, format) {
	return (moment(date)).format(format);
}