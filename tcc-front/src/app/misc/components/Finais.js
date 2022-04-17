/* react-web */
import React from 'react';
import Color from '../consts/fixeds/Color';
import Style from '../utils/Style';
import Texto from './Texto';

export default class Finais {}
Finais.noBinding = <Texto style={Style.create().color(Color.red).bold(true)} label={"Binding?"}/>;
