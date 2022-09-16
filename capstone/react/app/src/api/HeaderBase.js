export function getBaseHeaders(){
    return {'Content-Type': 'application/json',
            'x-csrf-token': window.CSRF_TOKEN_HEADER}
}