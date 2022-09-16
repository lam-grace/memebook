// //import {View,Image} from 'react-native';
// import React, { useEffect, useState } from 'react';
// import {getWebImage} from "../../api/ImageApi";
//
// const WebImage = ({imageId, altText, cssClass}) => {
//     const [imageData, setData] = useState('');
//     let finalAltText = altText !== undefined ? altText : imageId;
//     let finalClassName = cssClass !== undefined ? cssClass : "";
//     const getData = async () => {
//         getWebImage(imageId).then(imageData=>{
//             setData(imageData);
//             });
//     }
//
//     useEffect(() => {
//         getData();
//     }, []);
//     return (
//         // <View>
//         //     <Image source={{uri: data}} style={{width: 200, height: 200}}/>
//         // </View>
//         <>
//             {imageData !== undefined ? <img src={imageData} alt={finalAltText} className={finalClassName} />: ''}
//         </>
//     )
// }
// export default WebImage;
