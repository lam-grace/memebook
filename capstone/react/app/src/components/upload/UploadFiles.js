import React, {Component, useState} from "react";
import UploadService from "./UploadFilesService";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";

export default class UploadFiles extends Component {


    constructor(props) {
        super(props);
        this.selectFile = this.selectFile.bind(this);
        this.upload = this.upload.bind(this);

        this.state = {
            selectedFiles: undefined,
            currentFile: undefined,
            progress: 0,
            message: "",

            fileInfos: [],
        };
    }

    selectFile(event) {
        this.setState({
            selectedFiles: event.target.files,
        });
    }

    upload() {
        let currentFile = this.state.selectedFiles[0];

        this.setState({
            progress: 0,
            currentFile: currentFile,
        });

        UploadService.upload(currentFile, (event) => {
            this.setState({
                progress: Math.round((100 * event.loaded) / event.total),
                message: "File uploaded!",
            });
        })
            .then((response) => {
                // this.props.parentCallback(response.data);
            })
            .catch(() => {
                this.setState({
                    progress: 0,
                    message: "Could not upload the file!",
                    currentFile: undefined,
                });
            });

        this.setState({
            selectedFiles: undefined,
        });
    }

    render() {
        const {
            selectedFiles,
            currentFile,
            progress,
            message,
            fileInfos,
        } = this.state;

        return (
            <div>
                {currentFile && (
                    <div className="progress">
                        <div
                            className="progress-bar progress-bar-info progress-bar-striped"
                            role="progressbar"
                            aria-valuenow={progress}
                            aria-valuemin="0"
                            aria-valuemax="100"
                            style={{ width: progress + "%" }}
                        >
                            {progress}%
                        </div>
                    </div>
                )}

                <label className="btn btn-default">
                    <input type="file" onChange={this.selectFile} />
                </label>

                <button
                    className="btn btn-success"
                    disabled={!selectedFiles}
                    onClick={this.upload}
                >
                    Upload
                </button>

                <div className="alert alert-light" role="alert">
                    {message}
                </div>

                {/*<div className="card">*/}
                {/*    <div className="card-header">List of Files</div>*/}
                {/*    <ul className="list-group list-group-flush">*/}
                {/*        {fileInfos &&*/}
                {/*            fileInfos.map((file, index) => (*/}
                {/*                <li className="list-group-item" key={index}>*/}
                {/*                    <a href={file.url}>{file.name}</a>*/}
                {/*                </li>*/}
                {/*            ))}*/}
                {/*    </ul>*/}
                {/*</div>*/}
            {/*    <Modal show={showModal} onHide={handleCancel}>*/}
            {/*        <Modal.Header closeButton>*/}
            {/*            <Modal.Title>Congratulations!</Modal.Title>*/}
            {/*        </Modal.Header>*/}
            {/*        <Modal.Body>*/}
            {/*            Your profile has been updated*/}
            {/*        </Modal.Body>*/}
            {/*        <Modal.Footer>*/}
            {/*            <Button variant="secondary" href="/Home"> Return to Home Page</Button>*/}
            {/*        </Modal.Footer>*/}
            {/*    </Modal>*/}
            </div>


        );
    }
}