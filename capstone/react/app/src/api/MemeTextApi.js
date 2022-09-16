export function saveMemeText(memeText){
    const url = `/api/memeText`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'PUT',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
        body: JSON.stringify(memeText)
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}

export function getMemeText(id){
    const url = `/api/memeText/${id}`;
    return fetch(url, {
        mode: 'cors',
        credentials: 'same-origin',
        method: 'GET',
        headers: {  'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER},
    } ).then(response => {
        if (response.status === 200) {
            return response.json();
        } else {
            console.error(`Error: ${response.status}`);
        }
    }).catch(e => console.error(`Error: ${JSON.stringify(e)}`));
}