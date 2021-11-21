var pageNumber = 0, defaultSortingField = 'latestUpdate', defaultSortingDirection = 'DESC';
var previousPage, nextPage, sortingDirection = 'DESC', currentPage;
var buttonLeft = document.getElementById("button_left");
var buttonRight = document.getElementById("button_right");
var pageNumberElement = document.getElementById("page_number");
var fileChooser = document.getElementById("file_chooser");
var nameField = document.getElementById("name");
var searchField = document.getElementById("searchField");
var searchButton = document.getElementById("searchButton");


searchButton.onclick = () => {
    getSeriesSearch(0/*, defaultSortingField, defaultSortingDirection*/);
}
searchField.oninput = () => {
    if (searchField.value === "")  getSeries(0, defaultSortingField, defaultSortingDirection); else
        getSeriesSearch(0);
}

getSeries(0, defaultSortingField, defaultSortingDirection);

function updatePageable(currentPageNum, totalPages) {
    currentPage = currentPageNum;
    previousPage = currentPage - 1;
    if (previousPage < 0) {
        previousPage = 0;
    }
    nextPage = currentPage + 1;
    if (nextPage >= totalPages) {
        nextPage = totalPages - 1;
    }
    pageNumberElement.innerHTML = (currentPageNum + 1);
    buttonLeft.onclick = function () {
        if (searchField.value !== "") getSeriesSearch(previousPage); else
        getSeries(previousPage, defaultSortingField, defaultSortingDirection);
    }
    buttonRight.onclick = function () {
        if (searchField.value !== "") getSeriesSearch(nextPage); else
        getSeries(nextPage, defaultSortingField, defaultSortingDirection)
    };
}

function sort(field) {
    if (sortingDirection === 'ASC') {
        sortingDirection = 'DESC';
    } else {
        sortingDirection = 'ASC';
    }
    getSeries(currentPage, field, sortingDirection);
}



function getSeriesSearch(page) {
    if (page == null) {
        page = pageNumber;
    }

    var dataS = JSON.stringify({
        "index": page,
        "genres": searchField.value/*.split(" ")*/
    });
    console.log(dataS);
    $.ajax({
        type: "POST",
        contentType: 'application/json',
        data: dataS,
        dataType: 'json',
        url: "/series/genres/",
        success: function (data) {
        console.log(data);
        var container = document.getElementById("videos_container");
        var html = '';
        for (var i = 0; i < data.content.length; i++) {

            html +=
                '<div class="videos__self">' +
                '<img src="' + data.content[i].cover + '" alt="#">' +
                '<div class="videos__self_title">' +
                '<p title="' + data.content[i].name + '">' + data.content[i].name + '</p>' +
                '</div>' +
                '<a href="' + "/series/" + data.content[i].name + '"></a>' +
                '</div>'

        }
        container.innerHTML = html;

        updatePageable(data.number, data.totalPages);
    },
        error: function (data) {}
    });
}

function getSeries(page, sortingField, sortingDirection) {
    var pageSize = 8;
    if (page == null) {
        page = pageNumber;
    }
    if (sortingField == null) {
        sortingField = defaultSortingField;
    }
    if (sortingDirection == null) {
        sortingDirection = defaultSortingDirection
    }

    $.get("/series?page=" + page + "&size=" + pageSize + "&sortingField=" + sortingField + "&sortingDirection=" + sortingDirection, function (data, status) {
        console.log(data);
        var container = document.getElementById("videos_container");
        var html = '';
        for (var i = 0; i < data.content.length; i++) {

            html +=
                '<div class="videos__self">' +
                '<img src="' + data.content[i].cover + '" alt="#">' +
                '<div class="videos__self_title">' +
                '<p title="' + data.content[i].name + '">' + data.content[i].name + '</p>' +
                '</div>' +
                '<a href="' + "/series/" + data.content[i].name + '"></a>' +
                '</div>'

        }
        container.innerHTML = html;

        updatePageable(data.number, data.totalPages);
    });
}

