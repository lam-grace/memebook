export function getAllImages(){
    const url = `/api/image/all`;
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

export function getImage(id){
    const url = `/api/image/detail/${id}`;
    console.log(url);
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'GET'
    } )
        .then((response) => response.blob())
        .then((image) => {
            let blobUrl = URL.createObjectURL(image);
            console.log(blobUrl);
            return blobUrl;
        });
}

export function deleteImage(id){
    const url = `/api/image/delete/${id}`;
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


export function getImageList(searchString){
    const url = `/api/imageSearch/${searchString}`;
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


export function getPopularImageList(){
    const url = `/api/image/all/popular`;
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

export function getRecentImageList(){
    const url = `/api/image/all/recent`;
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

export function getAllTags(id){
    const url = `/api//image/allTags/${id}`;
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

export function getTagsByImageId(id){
    const url = `/api/image/tags/${id}`;
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

export function createTag(tag){
    const url = `/api/image/createTag`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(tag)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function assignTag(tag){
    const url = `/api/image/assignTag`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(tag)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function removeTag(imageId, tagId){
    const url = `/api/image/removeTag`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'POST',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(imageId, tagId)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

