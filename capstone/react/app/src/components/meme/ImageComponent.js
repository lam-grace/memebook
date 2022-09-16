import React, { useEffect, useState } from 'react';
import {getImage} from "../../api/ImageApi";

const ImageComponent = ({imageId, altText, cssClass}) => {
    const [imageData, setData] = useState('');
    let finalAltText = altText !== undefined ? altText : imageId;
    let finalClassName = cssClass !== undefined ? cssClass : "";
    const getData = async () => {
        getImage(imageId).then(imageData=>{
            setData(imageData);
        });
    }

    useEffect(() => {
        getData();
    }, [imageId]);
    return (

        <>
            {imageData !== undefined ? <img src={imageData} alt={finalAltText} className={finalClassName} />: ''}
        </>
    )
}
export default ImageComponent;
