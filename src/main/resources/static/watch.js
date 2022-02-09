var videoPlayer = document.getElementById("video_player");
var chooserList = document.getElementById("choser_list");
var cover = document.getElementById("poster");
var title = document.getElementById("title");
var description = document.getElementById("description");
var director = document.getElementById("director");
var genre = document.getElementById("genre");
var studio = document.getElementById("studio");
var releaseDate = document.getElementById("release_date");
var episodeAmount = document.getElementById("episode_amount");

getInfo();
function getInfo() {
    var currentUrl = window.location.href;
    $.get(currentUrl+"/get", function (data) {

        document.title = data.name;
        title.innerText = data.name;
        description.innerText = data.description;
        cover.src = data.cover;
        releaseDate.innerText = data.release_date;
        studio.innerText = "Студия: " + data.studio;
        director.innerText = "Режиссер: " + data.director;
        var genresString = "";
        data.genres.forEach(genreString => {
            genresString += genreString + " ";
        });
        genre.innerText = "Жанр: " + genresString;

        var episodes = data.episodes;
        changeEpisode(episodes[0].url);
        chooserList.innerHTML = null;
        episodes.forEach(episode => {
            var liElement = document.createElement("li");
            var buttonElement = document.createElement("button");
            buttonElement.className = "choser_button";
            buttonElement.innerText = episode.number;
            buttonElement.onclick = () => {changeEpisode(episode.url)};
            liElement.append(buttonElement);
            liElement.onclick = () => {changeEpisode(episode.url)};
            chooserList.append(liElement);
        })

        episodeAmount.innerText = "Количество серий: " + episodes.length;


    });
}

function changeEpisode(url) {
    videoPlayer.src = url;
}