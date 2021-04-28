// document.addEventListener('DOMContentLoaded', function () {
//     console.log('DOMContentLoaded');
// });

document.getElementById('saveBtn').addEventListener('click', clickSaveBtn);
document.getElementById('vacationType').addEventListener('change', changeVacationType);

function clickSaveBtn() {
    if (validateSave()) {
        save();
    }
}

/**
 * 휴가 등록 전 시작일, 종료일 입력 확인
 * @returns {boolean}
 */
function validateSave() {
    if (!document.getElementById('startDate').value) {
        alert('시작일자를 확인하세요.');
        return false;
    }

    if (document.getElementById('vacationType').value === '1' && !document.getElementById('endDate').value) {
        alert('종료일자를 확인하세요.');
        return false;
    }

    return true;
}

function save() {
    document.getElementById('requestVacationForm').submit();
}

/**
 * 연차 사용 시 종료일 입력 가능, 나머지는 종료일 입력 불가
 */
function changeVacationType() {
    var vacationType = document.getElementById('vacationType').value;
    if (vacationType === '1') {
        document.getElementById("endDate").disabled = false;
    } else {
        document.getElementById("endDate").disabled = true;
    }
}

