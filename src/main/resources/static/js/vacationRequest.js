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

function changeVacationType() {
    var vacationType = document.getElementById('vacationType').value;
    if (vacationType === '1') {
        document.getElementById("endDate").disabled = false;
    } else {
        document.getElementById("endDate").disabled = true;
    }
}

