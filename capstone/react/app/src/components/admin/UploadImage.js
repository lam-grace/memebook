import React, {useState} from "react";
import Form from 'react-bootstrap/Form';
import {Button} from "react-bootstrap";
import UploadFiles from "../upload/UploadFiles";
// import {saveImage} from "../../api/ImageApi";
import {useParams} from "react-router-dom";
import WebImage from "../webimage/WebImage";
import Modal from "react-bootstrap/Modal";


const UploadImage = () => {



    return (
        <>
            <Form className={"form"}>
                <UploadFiles></UploadFiles>
                <br/>
            </Form>

        </>

    );
};

export default UploadImage;
