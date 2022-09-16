import React, { useEffect, useState } from 'react';
import {getMemeImage} from "../../api/MemeApi";

const MemeComponent = ({memeId, altText, cssClass}) => {
    const [memeData, setData] = useState('');
    let finalAltText = altText !== undefined ? altText : memeId;
    let finalClassName = cssClass !== undefined ? cssClass : "";
    const getData = async () => {
        getMemeImage(memeId).then(memeData=>{
            setData(memeData);
        });
    }

    useEffect(() => {
        getData();
    }, [memeId]);
    return (

        <>
            {memeData !== undefined ? <img src={memeData} alt={finalAltText} className={finalClassName} />: ''}
        </>
    )
}
export default MemeComponent;
