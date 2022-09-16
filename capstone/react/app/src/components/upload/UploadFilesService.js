import http from "./HttpAxiosFileUpload";

class UploadFilesService {
    upload(file, onUploadProgress) {
        let formData = new FormData();

        formData.append("file", file);

        return http.post("/api/upload", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
                "x-csrf-token": window.CSRF_TOKEN_HEADER
            },
            onUploadProgress,
        });
    }
}

export default new UploadFilesService();