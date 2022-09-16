export function getMovieList(searchString){
    const url = `/api/movieSearch/${searchString}`;
    console.log(url);
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'GET',
        headers: {  'Content-Type': 'application/json',
                    'x-csrf-token': window.CSRF_TOKEN_HEADER}
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else if (response.status === 403) {
            return [];
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function getAllMovies(){
    const url = `/api/movie/all`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'GET',
        headers: {  'Content-Type': 'application/json',
                    'x-csrf-token': window.CSRF_TOKEN_HEADER}
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function getMovieDetail(id){
    const url = `/api/movie/detail/${id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'GET',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER}
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}
export function saveMovieDetail(movie){
    const url = `/api/movie/detail/${movie.id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'PUT',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(movie)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}
