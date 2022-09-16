export function getAllMemes(){
    const url = `/api/meme/all`;
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

export function getMeme(id){
    const url = `/api/meme/${id}`;
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

export function getTopMemes(){
    const url = `/api/meme/top6`;
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

export function getMemeImage(id){
    const url = `/api/meme/detail/${id}`;
    console.log(url);
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'GET',
    } ).then((response) => response.blob())
        .then((meme) => {
            let blobUrl = URL.createObjectURL(meme);
            console.log(blobUrl);
            return blobUrl;
        });
}

export function getMemeList(searchString){
    const url = `/api/memeSearch/${searchString}`;
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

export function getUnapprovedMemes(){
    const url = `/api/meme/unapproved`;
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

export function getPopularMemeList(){
    const url = `/api/meme/all/popular`;
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

export function getRecentMemeList(){
    const url = `/api/meme/all/recent`;
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

export function likeMeme(id){
    const url = `/api/meme/like/${id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(id)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function unlikeMeme(id){
    const url = `/api/meme/unlike/${id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(id)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function approveMeme(id){
    const url = `/api/meme/approve/${id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(id)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function deleteMeme(id){
    const url = `/api/meme/delete/${id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(id)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function reportMeme(id){
    const url = `/api/meme/report/${id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(id)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function createMeme(meme){
    const url = `/api/createMeme`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(meme)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}