/* react */
import AlignItens from '../consts/enums/AlignItens';
import Console from './Console';
import FontStyle from '../consts/enums/FontStyle';
import FontWeight from '../consts/enums/FontWeight';
import Overflow from '../consts/enums/Overflow';
import TextAlign from '../consts/enums/TextAlign';
import TextDecorationLine from '../consts/enums/TextDecorationLine';
import UCommons from './UCommons';
import UDouble from './UDouble';
import UString from './UString';

export default class Style {

	static modelo;

	static create() {

		let result;

		if (UCommons.isEmpty(Style.modelo)) {
			result = new Style();
			result.o = {};
		} else {
			result = Style.modelo.copy();
		}

		return result;
	}
	border(value) {
		this.borderColor(value.color);
		this.borderStyle(value.style);
		this.borderWidth(value.width);
		return this;
	}
	borderBottom(value) {
		this.borderBottomColor(value.color);
		this.borderBottomStyle(value.style);
		this.borderBottomWidth(value.width);
		return this;
	}
	borderTop(value) {
		this.borderTopColor(value.color);
		this.borderTopStyle(value.style);
		this.borderTopWidth(value.width);
		return this;
	}
	borderLeft(value) {
		this.borderLeftColor(value.color);
		this.borderLeftStyle(value.style);
		this.borderLeftWidth(value.width);
		return this;
	}
	borderRight(value) {
		this.borderRightColor(value.color);
		this.borderRightStyle(value.style);
		this.borderRightWidth(value.width);
		return this;
	}
	scrollY() {
		return this.overflowY(Overflow.auto);
	}
	scrollX() {
		return this.overflowX(Overflow.auto);
	}
	textAlignCenter() {
		return this.textAlign(TextAlign.center);
	}
	alignItemsCenter() {
		return this.alignItems(AlignItens.center);
	}
	shadowOffset(width, height) {
		this.checkLock();
		this.o.shadowOffset = {width: width, height: height};
		return this;
	}
	textShadowOffset(width, height) {
		this.checkLock();
		this.o.textShadowOffset = {width: width, height: height};
		return this;
	}
	paddingLeftRight(value) {
		return this.paddingLeft(value).paddingRight(value);
	}
	getHeight() {
		const height = this.get().height;
		if (UCommons.isEmpty(height)) {
			return 0;
		} else if (UCommons.equals(typeof(height), "string")){
			const s = height;
			if (s.endsWith("%")) {
				return null;
			}
		}
		return parseInt(height);
	}

	getHeightPercent() {
		const s = this.get().height;
		if (UString.isEmpty(s) || !s.endsWith("%")) {
			return null;
		} else {
			return parseInt(UString.ignoreRight(s, 1));
		}
	}

	getFontSize() {
		return this.get().fontSize;
	}

	debugar = false;
	semScale = false;
	locked = false;

	static scala = 1;
	ultimaScala = 1;

	static ajusteFontSize = 0;
	ultimoAjusteFontSize = 0;

	lock() {
		this.locked = true;
		return this;
	}

	debug() {
		this.debugar = true;
		return this;
	}

	noScale() {
		this.semScale = true;
		return this;
	}
	bold(value) {
		return this.fontWeight(value ? FontWeight.bold : FontWeight.normal);
	}
	italic(value) {
		return this.fontStyle(value ? FontStyle.italic : FontStyle.italic);
	}
	underline(value) {
		return this.textDecorationLine(value ? TextDecorationLine.underline : TextDecorationLine.underline);
	}

	checkLock() {
		if (this.locked) {
			throw new Error("locked");
		}
	}
	get() {

		if (this.debugar) {
			Console.log("Style.debugar", this);
		}

		if (this.semScale || UCommons.equals(Style.scala, 1.0)) {
			if (UCommons.equals(Style.ajusteFontSize, 0)) {
				return this.o;
			} else {
				if (UCommons.notEquals(Style.ajusteFontSize, this.ultimoAjusteFontSize)) {
					this.ultimoGet = this.copy().o;
					if (UCommons.notEmpty(this.o.fontSize)) {
						this.ultimoGet.fontSize = (this.o.fontSize + Style.ajusteFontSize);
					}
					this.ultimoAjusteFontSize = Style.ajusteFontSize;
				}
				return this.ultimoGet;
			}
		} else {
			if (UCommons.notEquals(Style.scala, this.ultimaScala) || UCommons.notEquals(this.ultimoAjusteFontSize, Style.ajusteFontSize)) {
				this.atualizaUltimoGet();
				this.ultimoAjusteFontSize = Style.ajusteFontSize;
			}
			return this.ultimoGet;
		}
	}

	o = {};
	ultimoGet = this.o;
	atualizaUltimoGet() {
		this.ultimoGet = {};
		if (UCommons.notEmpty(this.o.flex)) {
			if (UString.equals(typeof(this.o.flex), "number")) {
				const value = UDouble.toDouble(this.o.flex);
				this.ultimoGet.flex = value * Style.scala;
			} else {
				this.ultimoGet.flex = this.o.flex;
			}
		}
		if (UCommons.notEmpty(this.o.width)) {
			if (UString.equals(typeof(this.o.width), "number")) {
				const value = UDouble.toDouble(this.o.width);
				this.ultimoGet.width = value * Style.scala;
			} else {
				this.ultimoGet.width = this.o.width;
			}
		}
		if (UCommons.notEmpty(this.o.margin)) {
			if (UString.equals(typeof(this.o.margin), "number")) {
				const value = UDouble.toDouble(this.o.margin);
				this.ultimoGet.margin = value * Style.scala;
			} else {
				this.ultimoGet.margin = this.o.margin;
			}
		}
		if (UCommons.notEmpty(this.o.minHeight)) {
			if (UString.equals(typeof(this.o.minHeight), "number")) {
				const value = UDouble.toDouble(this.o.minHeight);
				this.ultimoGet.minHeight = value * Style.scala;
			} else {
				this.ultimoGet.minHeight = this.o.minHeight;
			}
		}
		if (UCommons.notEmpty(this.o.maxHeight)) {
			if (UString.equals(typeof(this.o.maxHeight), "number")) {
				const value = UDouble.toDouble(this.o.maxHeight);
				this.ultimoGet.maxHeight = value * Style.scala;
			} else {
				this.ultimoGet.maxHeight = this.o.maxHeight;
			}
		}
		if (UCommons.notEmpty(this.o.height)) {
			if (UString.equals(typeof(this.o.height), "number")) {
				const value = UDouble.toDouble(this.o.height);
				this.ultimoGet.height = value * Style.scala;
			} else {
				this.ultimoGet.height = this.o.height;
			}
		}
		if (UCommons.notEmpty(this.o.zIndex)) {
			if (UString.equals(typeof(this.o.zIndex), "number")) {
				const value = UDouble.toDouble(this.o.zIndex);
				this.ultimoGet.zIndex = value * Style.scala;
			} else {
				this.ultimoGet.zIndex = this.o.zIndex;
			}
		}
		if (UCommons.notEmpty(this.o.bottom)) {
			if (UString.equals(typeof(this.o.bottom), "number")) {
				const value = UDouble.toDouble(this.o.bottom);
				this.ultimoGet.bottom = value * Style.scala;
			} else {
				this.ultimoGet.bottom = this.o.bottom;
			}
		}
		if (UCommons.notEmpty(this.o.minWidth)) {
			if (UString.equals(typeof(this.o.minWidth), "number")) {
				const value = UDouble.toDouble(this.o.minWidth);
				this.ultimoGet.minWidth = value * Style.scala;
			} else {
				this.ultimoGet.minWidth = this.o.minWidth;
			}
		}
		if (UCommons.notEmpty(this.o.maxWidth)) {
			if (UString.equals(typeof(this.o.maxWidth), "number")) {
				const value = UDouble.toDouble(this.o.maxWidth);
				this.ultimoGet.maxWidth = value * Style.scala;
			} else {
				this.ultimoGet.maxWidth = this.o.maxWidth;
			}
		}
		if (UCommons.notEmpty(this.o.opacity)) {
			if (UString.equals(typeof(this.o.opacity), "number")) {
				const value = UDouble.toDouble(this.o.opacity);
				this.ultimoGet.opacity = value * Style.scala;
			} else {
				this.ultimoGet.opacity = this.o.opacity;
			}
		}
		if (UCommons.notEmpty(this.o.top)) {
			if (UString.equals(typeof(this.o.top), "number")) {
				const value = UDouble.toDouble(this.o.top);
				this.ultimoGet.top = value * Style.scala;
			} else {
				this.ultimoGet.top = this.o.top;
			}
		}
		if (UCommons.notEmpty(this.o.marginTop)) {
			if (UString.equals(typeof(this.o.marginTop), "number")) {
				const value = UDouble.toDouble(this.o.marginTop);
				this.ultimoGet.marginTop = value * Style.scala;
			} else {
				this.ultimoGet.marginTop = this.o.marginTop;
			}
		}
		if (UCommons.notEmpty(this.o.marginLeft)) {
			if (UString.equals(typeof(this.o.marginLeft), "number")) {
				const value = UDouble.toDouble(this.o.marginLeft);
				this.ultimoGet.marginLeft = value * Style.scala;
			} else {
				this.ultimoGet.marginLeft = this.o.marginLeft;
			}
		}
		if (UCommons.notEmpty(this.o.marginRight)) {
			if (UString.equals(typeof(this.o.marginRight), "number")) {
				const value = UDouble.toDouble(this.o.marginRight);
				this.ultimoGet.marginRight = value * Style.scala;
			} else {
				this.ultimoGet.marginRight = this.o.marginRight;
			}
		}
		if (UCommons.notEmpty(this.o.marginBottom)) {
			if (UString.equals(typeof(this.o.marginBottom), "number")) {
				const value = UDouble.toDouble(this.o.marginBottom);
				this.ultimoGet.marginBottom = value * Style.scala;
			} else {
				this.ultimoGet.marginBottom = this.o.marginBottom;
			}
		}
		if (UCommons.notEmpty(this.o.marginHorizontal)) {
			if (UString.equals(typeof(this.o.marginHorizontal), "number")) {
				const value = UDouble.toDouble(this.o.marginHorizontal);
				this.ultimoGet.marginHorizontal = value * Style.scala;
			} else {
				this.ultimoGet.marginHorizontal = this.o.marginHorizontal;
			}
		}
		if (UCommons.notEmpty(this.o.padding)) {
			if (UString.equals(typeof(this.o.padding), "number")) {
				const value = UDouble.toDouble(this.o.padding);
				this.ultimoGet.padding = value * Style.scala;
			} else {
				this.ultimoGet.padding = this.o.padding;
			}
		}
		if (UCommons.notEmpty(this.o.paddingTop)) {
			if (UString.equals(typeof(this.o.paddingTop), "number")) {
				const value = UDouble.toDouble(this.o.paddingTop);
				this.ultimoGet.paddingTop = value * Style.scala;
			} else {
				this.ultimoGet.paddingTop = this.o.paddingTop;
			}
		}
		if (UCommons.notEmpty(this.o.paddingLeft)) {
			if (UString.equals(typeof(this.o.paddingLeft), "number")) {
				const value = UDouble.toDouble(this.o.paddingLeft);
				this.ultimoGet.paddingLeft = value * Style.scala;
			} else {
				this.ultimoGet.paddingLeft = this.o.paddingLeft;
			}
		}
		if (UCommons.notEmpty(this.o.paddingRight)) {
			if (UString.equals(typeof(this.o.paddingRight), "number")) {
				const value = UDouble.toDouble(this.o.paddingRight);
				this.ultimoGet.paddingRight = value * Style.scala;
			} else {
				this.ultimoGet.paddingRight = this.o.paddingRight;
			}
		}
		if (UCommons.notEmpty(this.o.paddingBottom)) {
			if (UString.equals(typeof(this.o.paddingBottom), "number")) {
				const value = UDouble.toDouble(this.o.paddingBottom);
				this.ultimoGet.paddingBottom = value * Style.scala;
			} else {
				this.ultimoGet.paddingBottom = this.o.paddingBottom;
			}
		}
		if (UCommons.notEmpty(this.o.paddingVertical)) {
			if (UString.equals(typeof(this.o.paddingVertical), "number")) {
				const value = UDouble.toDouble(this.o.paddingVertical);
				this.ultimoGet.paddingVertical = value * Style.scala;
			} else {
				this.ultimoGet.paddingVertical = this.o.paddingVertical;
			}
		}
		if (UCommons.notEmpty(this.o.paddingHorizontal)) {
			if (UString.equals(typeof(this.o.paddingHorizontal), "number")) {
				const value = UDouble.toDouble(this.o.paddingHorizontal);
				this.ultimoGet.paddingHorizontal = value * Style.scala;
			} else {
				this.ultimoGet.paddingHorizontal = this.o.paddingHorizontal;
			}
		}
		if (UCommons.notEmpty(this.o.borderWidth)) {
			if (UString.equals(typeof(this.o.borderWidth), "number")) {
				const value = UDouble.toDouble(this.o.borderWidth);
				this.ultimoGet.borderWidth = value * Style.scala;
			} else {
				this.ultimoGet.borderWidth = this.o.borderWidth;
			}
		}
		if (UCommons.notEmpty(this.o.borderLeftWidth)) {
			if (UString.equals(typeof(this.o.borderLeftWidth), "number")) {
				const value = UDouble.toDouble(this.o.borderLeftWidth);
				this.ultimoGet.borderLeftWidth = value * Style.scala;
			} else {
				this.ultimoGet.borderLeftWidth = this.o.borderLeftWidth;
			}
		}
		if (UCommons.notEmpty(this.o.borderRightWidth)) {
			if (UString.equals(typeof(this.o.borderRightWidth), "number")) {
				const value = UDouble.toDouble(this.o.borderRightWidth);
				this.ultimoGet.borderRightWidth = value * Style.scala;
			} else {
				this.ultimoGet.borderRightWidth = this.o.borderRightWidth;
			}
		}
		if (UCommons.notEmpty(this.o.borderTopWidth)) {
			if (UString.equals(typeof(this.o.borderTopWidth), "number")) {
				const value = UDouble.toDouble(this.o.borderTopWidth);
				this.ultimoGet.borderTopWidth = value * Style.scala;
			} else {
				this.ultimoGet.borderTopWidth = this.o.borderTopWidth;
			}
		}
		if (UCommons.notEmpty(this.o.borderBottomWidth)) {
			if (UString.equals(typeof(this.o.borderBottomWidth), "number")) {
				const value = UDouble.toDouble(this.o.borderBottomWidth);
				this.ultimoGet.borderBottomWidth = value * Style.scala;
			} else {
				this.ultimoGet.borderBottomWidth = this.o.borderBottomWidth;
			}
		}
		if (UCommons.notEmpty(this.o.borderRadius)) {
			if (UString.equals(typeof(this.o.borderRadius), "number")) {
				const value = UDouble.toDouble(this.o.borderRadius);
				this.ultimoGet.borderRadius = value * Style.scala;
			} else {
				this.ultimoGet.borderRadius = this.o.borderRadius;
			}
		}
		if (UCommons.notEmpty(this.o.fontSize)) {
			this.ultimoGet.fontSize = (this.o.fontSize + Style.ajusteFontSize) * Style.scala;
		}
		if (UCommons.notEmpty(this.o.color)) {
			this.ultimoGet.color = this.o.color;
		}
		if (UCommons.notEmpty(this.o.textColor)) {
			this.ultimoGet.textColor = this.o.textColor;
		}
		if (UCommons.notEmpty(this.o.background)) {
			this.ultimoGet.background = this.o.background;
		}
		if (UCommons.notEmpty(this.o.shadowColor)) {
			this.ultimoGet.shadowColor = this.o.shadowColor;
		}
		if (UCommons.notEmpty(this.o.textShadowColor)) {
			this.ultimoGet.textShadowColor = this.o.textShadowColor;
		}
		if (UCommons.notEmpty(this.o.borderColor)) {
			this.ultimoGet.borderColor = this.o.borderColor;
		}
		if (UCommons.notEmpty(this.o.borderTopColor)) {
			this.ultimoGet.borderTopColor = this.o.borderTopColor;
		}
		if (UCommons.notEmpty(this.o.borderBottomColor)) {
			this.ultimoGet.borderBottomColor = this.o.borderBottomColor;
		}
		if (UCommons.notEmpty(this.o.borderLeftColor)) {
			this.ultimoGet.borderLeftColor = this.o.borderLeftColor;
		}
		if (UCommons.notEmpty(this.o.borderRightColor)) {
			this.ultimoGet.borderRightColor = this.o.borderRightColor;
		}
		if (UCommons.notEmpty(this.o.backgroundColor)) {
			this.ultimoGet.backgroundColor = this.o.backgroundColor;
		}
		if (UCommons.notEmpty(this.o.textTransform)) {
			this.ultimoGet.textTransform = this.o.textTransform;
		}
		if (UCommons.notEmpty(this.o.position)) {
			this.ultimoGet.position = this.o.position;
		}
		if (UCommons.notEmpty(this.o.textAlign)) {
			this.ultimoGet.textAlign = this.o.textAlign;
		}
		if (UCommons.notEmpty(this.o.verticalAlign)) {
			this.ultimoGet.verticalAlign = this.o.verticalAlign;
		}
		if (UCommons.notEmpty(this.o.alignItems)) {
			this.ultimoGet.alignItems = this.o.alignItems;
		}
		if (UCommons.notEmpty(this.o.alignSelf)) {
			this.ultimoGet.alignSelf = this.o.alignSelf;
		}
		if (UCommons.notEmpty(this.o.fontWeight)) {
			this.ultimoGet.fontWeight = this.o.fontWeight;
		}
		if (UCommons.notEmpty(this.o.fontStyle)) {
			this.ultimoGet.fontStyle = this.o.fontStyle;
		}
		if (UCommons.notEmpty(this.o.textDecorationLine)) {
			this.ultimoGet.textDecorationLine = this.o.textDecorationLine;
		}
		if (UCommons.notEmpty(this.o.borderStyle)) {
			this.ultimoGet.borderStyle = this.o.borderStyle;
		}
		if (UCommons.notEmpty(this.o.borderBottomStyle)) {
			this.ultimoGet.borderBottomStyle = this.o.borderBottomStyle;
		}
		if (UCommons.notEmpty(this.o.borderLeftStyle)) {
			this.ultimoGet.borderLeftStyle = this.o.borderLeftStyle;
		}
		if (UCommons.notEmpty(this.o.borderTopStyle)) {
			this.ultimoGet.borderTopStyle = this.o.borderTopStyle;
		}
		if (UCommons.notEmpty(this.o.borderRightStyle)) {
			this.ultimoGet.borderRightStyle = this.o.borderRightStyle;
		}
		if (UCommons.notEmpty(this.o.flexDirection)) {
			this.ultimoGet.flexDirection = this.o.flexDirection;
		}
		if (UCommons.notEmpty(this.o.justifyContent)) {
			this.ultimoGet.justifyContent = this.o.justifyContent;
		}
		if (UCommons.notEmpty(this.o.visibility)) {
			this.ultimoGet.visibility = this.o.visibility;
		}
		if (UCommons.notEmpty(this.o.float)) {
			this.ultimoGet.float = this.o.float;
		}
		if (UCommons.notEmpty(this.o.overflow)) {
			this.ultimoGet.overflow = this.o.overflow;
		}
		if (UCommons.notEmpty(this.o.overflowY)) {
			this.ultimoGet.overflowY = this.o.overflowY;
		}
		if (UCommons.notEmpty(this.o.overflowX)) {
			this.ultimoGet.overflowX = this.o.overflowX;
		}
		if (UCommons.notEmpty(this.o.display)) {
			this.ultimoGet.display = this.o.display;
		}
		if (UCommons.notEmpty(this.o.flexGrow)) {
			this.ultimoGet.flexGrow = this.o.flexGrow;
		}
		if (UCommons.notEmpty(this.o.left)) {
			this.ultimoGet.left = this.o.left;
		}
		if (UCommons.notEmpty(this.o.right)) {
			this.ultimoGet.right = this.o.right;
		}
		if (UCommons.notEmpty(this.o.lineHeight)) {
			this.ultimoGet.lineHeight = this.o.lineHeight;
		}
		if (UCommons.notEmpty(this.o.cursor)) {
			this.ultimoGet.cursor = this.o.cursor;
		}
		if (UCommons.notEmpty(this.o.listStyleType)) {
			this.ultimoGet.listStyleType = this.o.listStyleType;
		}
		if (UCommons.notEmpty(this.o.flexWrap)) {
			this.ultimoGet.flexWrap = this.o.flexWrap;
		}
		if (UCommons.notEmpty(this.o.resizeMode)) {
			this.ultimoGet.resizeMode = this.o.resizeMode;
		}
		if (UCommons.notEmpty(this.o.fontFamily)) {
			this.ultimoGet.fontFamily = this.o.fontFamily;
		}
		if (UCommons.notEmpty(this.o.elevation)) {
			this.ultimoGet.elevation = this.o.elevation;
		}
		if (UCommons.notEmpty(this.o.shadowRadius)) {
			this.ultimoGet.shadowRadius = this.o.shadowRadius;
		}
		if (UCommons.notEmpty(this.o.textShadowRadius)) {
			this.ultimoGet.textShadowRadius = this.o.textShadowRadius;
		}
		if (UCommons.notEmpty(this.o.shadowOpacity)) {
			this.ultimoGet.shadowOpacity = this.o.shadowOpacity;
		}
		if (UCommons.notEmpty(this.o.textShadowOpacity)) {
			this.ultimoGet.textShadowOpacity = this.o.textShadowOpacity;
		}
		if (UCommons.notEmpty(this.o.aspectRatio)) {
			this.ultimoGet.aspectRatio = this.o.aspectRatio;
		}
		this.ultimaScala = Style.scala;
	}
	flex(value) {
		this.o.flex = value;
		return this;
	}
	flexPercent(value) {
		this.checkLock();
		this.o.flex = value + "%";
		return this;
	}
	width(value) {
		this.o.width = value;
		return this;
	}
	widthPercent(value) {
		this.checkLock();
		this.o.width = value + "%";
		return this;
	}
	margin(value) {
		this.o.margin = value;
		return this;
	}
	marginPercent(value) {
		this.checkLock();
		this.o.margin = value + "%";
		return this;
	}
	minHeight(value) {
		this.o.minHeight = value;
		return this;
	}
	minHeightPercent(value) {
		this.checkLock();
		this.o.minHeight = value + "%";
		return this;
	}
	maxHeight(value) {
		this.o.maxHeight = value;
		return this;
	}
	maxHeightPercent(value) {
		this.checkLock();
		this.o.maxHeight = value + "%";
		return this;
	}
	height(value) {
		this.o.height = value;
		return this;
	}
	heightPercent(value) {
		this.checkLock();
		this.o.height = value + "%";
		return this;
	}
	zIndex(value) {
		this.o.zIndex = value;
		return this;
	}
	zIndexPercent(value) {
		this.checkLock();
		this.o.zIndex = value + "%";
		return this;
	}
	bottom(value) {
		this.o.bottom = value;
		return this;
	}
	bottomPercent(value) {
		this.checkLock();
		this.o.bottom = value + "%";
		return this;
	}
	minWidth(value) {
		this.o.minWidth = value;
		return this;
	}
	minWidthPercent(value) {
		this.checkLock();
		this.o.minWidth = value + "%";
		return this;
	}
	maxWidth(value) {
		this.o.maxWidth = value;
		return this;
	}
	maxWidthPercent(value) {
		this.checkLock();
		this.o.maxWidth = value + "%";
		return this;
	}
	opacity(value) {
		this.o.opacity = value;
		return this;
	}
	opacityPercent(value) {
		this.checkLock();
		this.o.opacity = value + "%";
		return this;
	}
	top(value) {
		this.o.top = value;
		return this;
	}
	topPercent(value) {
		this.checkLock();
		this.o.top = value + "%";
		return this;
	}
	marginTop(value) {
		this.o.marginTop = value;
		return this;
	}
	marginTopPercent(value) {
		this.checkLock();
		this.o.marginTop = value + "%";
		return this;
	}
	marginLeft(value) {
		this.o.marginLeft = value;
		return this;
	}
	marginLeftPercent(value) {
		this.checkLock();
		this.o.marginLeft = value + "%";
		return this;
	}
	marginRight(value) {
		this.o.marginRight = value;
		return this;
	}
	marginRightPercent(value) {
		this.checkLock();
		this.o.marginRight = value + "%";
		return this;
	}
	marginBottom(value) {
		this.o.marginBottom = value;
		return this;
	}
	marginBottomPercent(value) {
		this.checkLock();
		this.o.marginBottom = value + "%";
		return this;
	}
	marginHorizontal(value) {
		this.o.marginHorizontal = value;
		return this;
	}
	marginHorizontalPercent(value) {
		this.checkLock();
		this.o.marginHorizontal = value + "%";
		return this;
	}
	padding(value) {
		this.o.padding = value;
		return this;
	}
	paddingPercent(value) {
		this.checkLock();
		this.o.padding = value + "%";
		return this;
	}
	paddingTop(value) {
		this.o.paddingTop = value;
		return this;
	}
	paddingTopPercent(value) {
		this.checkLock();
		this.o.paddingTop = value + "%";
		return this;
	}
	paddingLeft(value) {
		this.o.paddingLeft = value;
		return this;
	}
	paddingLeftPercent(value) {
		this.checkLock();
		this.o.paddingLeft = value + "%";
		return this;
	}
	paddingRight(value) {
		this.o.paddingRight = value;
		return this;
	}
	paddingRightPercent(value) {
		this.checkLock();
		this.o.paddingRight = value + "%";
		return this;
	}
	paddingBottom(value) {
		this.o.paddingBottom = value;
		return this;
	}
	paddingBottomPercent(value) {
		this.checkLock();
		this.o.paddingBottom = value + "%";
		return this;
	}
	paddingVertical(value) {
		this.o.paddingVertical = value;
		return this;
	}
	paddingVerticalPercent(value) {
		this.checkLock();
		this.o.paddingVertical = value + "%";
		return this;
	}
	paddingHorizontal(value) {
		this.o.paddingHorizontal = value;
		return this;
	}
	paddingHorizontalPercent(value) {
		this.checkLock();
		this.o.paddingHorizontal = value + "%";
		return this;
	}
	borderWidth(value) {
		this.o.borderWidth = value;
		return this;
	}
	borderWidthPercent(value) {
		this.checkLock();
		this.o.borderWidth = value + "%";
		return this;
	}
	borderLeftWidth(value) {
		this.o.borderLeftWidth = value;
		return this;
	}
	borderLeftWidthPercent(value) {
		this.checkLock();
		this.o.borderLeftWidth = value + "%";
		return this;
	}
	borderRightWidth(value) {
		this.o.borderRightWidth = value;
		return this;
	}
	borderRightWidthPercent(value) {
		this.checkLock();
		this.o.borderRightWidth = value + "%";
		return this;
	}
	borderTopWidth(value) {
		this.o.borderTopWidth = value;
		return this;
	}
	borderTopWidthPercent(value) {
		this.checkLock();
		this.o.borderTopWidth = value + "%";
		return this;
	}
	borderBottomWidth(value) {
		this.o.borderBottomWidth = value;
		return this;
	}
	borderBottomWidthPercent(value) {
		this.checkLock();
		this.o.borderBottomWidth = value + "%";
		return this;
	}
	borderRadius(value) {
		this.o.borderRadius = value;
		return this;
	}
	borderRadiusPercent(value) {
		this.checkLock();
		this.o.borderRadius = value + "%";
		return this;
	}
	color(value) {
		this.checkLock();
		this.o.color = value;
		return this;
	}
	textColor(value) {
		this.checkLock();
		this.o.textColor = value;
		return this;
	}
	background(value) {
		this.checkLock();
		this.o.background = value;
		return this;
	}
	shadowColor(value) {
		this.checkLock();
		this.o.shadowColor = value;
		return this;
	}
	textShadowColor(value) {
		this.checkLock();
		this.o.textShadowColor = value;
		return this;
	}
	borderColor(value) {
		this.checkLock();
		this.o.borderColor = value;
		return this;
	}
	borderTopColor(value) {
		this.checkLock();
		this.o.borderTopColor = value;
		return this;
	}
	borderBottomColor(value) {
		this.checkLock();
		this.o.borderBottomColor = value;
		return this;
	}
	borderLeftColor(value) {
		this.checkLock();
		this.o.borderLeftColor = value;
		return this;
	}
	borderRightColor(value) {
		this.checkLock();
		this.o.borderRightColor = value;
		return this;
	}
	backgroundColor(value) {
		this.checkLock();
		this.o.backgroundColor = value;
		return this;
	}
	textTransform(value) {
		this.checkLock();
		this.o.textTransform = value;
		return this;
	}
	position(value) {
		this.checkLock();
		this.o.position = value;
		return this;
	}
	textAlign(value) {
		this.checkLock();
		this.o.textAlign = value;
		return this;
	}
	verticalAlign(value) {
		this.checkLock();
		this.o.verticalAlign = value;
		return this;
	}
	alignItems(value) {
		this.checkLock();
		this.o.alignItems = value;
		return this;
	}
	alignSelf(value) {
		this.checkLock();
		this.o.alignSelf = value;
		return this;
	}
	fontWeight(value) {
		this.checkLock();
		this.o.fontWeight = value;
		return this;
	}
	fontStyle(value) {
		this.checkLock();
		this.o.fontStyle = value;
		return this;
	}
	textDecorationLine(value) {
		this.checkLock();
		this.o.textDecorationLine = value;
		return this;
	}
	borderStyle(value) {
		this.checkLock();
		this.o.borderStyle = value;
		return this;
	}
	borderBottomStyle(value) {
		this.checkLock();
		this.o.borderBottomStyle = value;
		return this;
	}
	borderLeftStyle(value) {
		this.checkLock();
		this.o.borderLeftStyle = value;
		return this;
	}
	borderTopStyle(value) {
		this.checkLock();
		this.o.borderTopStyle = value;
		return this;
	}
	borderRightStyle(value) {
		this.checkLock();
		this.o.borderRightStyle = value;
		return this;
	}
	flexDirection(value) {
		this.checkLock();
		this.o.flexDirection = value;
		return this;
	}
	justifyContent(value) {
		this.checkLock();
		this.o.justifyContent = value;
		return this;
	}
	visibility(value) {
		this.checkLock();
		this.o.visibility = value;
		return this;
	}
	float(value) {
		this.checkLock();
		this.o.float = value;
		return this;
	}
	overflow(value) {
		this.checkLock();
		this.o.overflow = value;
		return this;
	}
	overflowY(value) {
		this.checkLock();
		this.o.overflowY = value;
		return this;
	}
	overflowX(value) {
		this.checkLock();
		this.o.overflowX = value;
		return this;
	}
	display(value) {
		this.checkLock();
		this.o.display = value;
		return this;
	}
	flexGrow(value) {
		this.checkLock();
		this.o.flexGrow = value;
		return this;
	}
	left(value) {
		this.checkLock();
		this.o.left = value;
		return this;
	}
	right(value) {
		this.checkLock();
		this.o.right = value;
		return this;
	}
	lineHeight(value) {
		this.checkLock();
		this.o.lineHeight = value;
		return this;
	}
	cursor(value) {
		this.checkLock();
		this.o.cursor = value;
		return this;
	}
	listStyleType(value) {
		this.checkLock();
		this.o.listStyleType = value;
		return this;
	}
	flexWrap(value) {
		this.checkLock();
		this.o.flexWrap = value;
		return this;
	}
	resizeMode(value) {
		this.checkLock();
		this.o.resizeMode = value;
		return this;
	}
	fontFamily(value) {
		this.checkLock();
		this.o.fontFamily = value;
		return this;
	}
	elevation(value) {
		this.checkLock();
		this.o.elevation = value;
		return this;
	}
	shadowRadius(value) {
		this.checkLock();
		this.o.shadowRadius = value;
		return this;
	}
	textShadowRadius(value) {
		this.checkLock();
		this.o.textShadowRadius = value;
		return this;
	}
	shadowOpacity(value) {
		this.checkLock();
		this.o.shadowOpacity = value;
		return this;
	}
	textShadowOpacity(value) {
		this.checkLock();
		this.o.textShadowOpacity = value;
		return this;
	}
	aspectRatio(value) {
		this.checkLock();
		this.o.aspectRatio = value;
		return this;
	}
	fontSize(value) {
		this.checkLock();
		this.o.fontSize = value;
		return this;
	}
	copy() {
		const style = new Style();
		if (UCommons.notEmpty(this.o.color)) {
			style.o.color = this.o.color;
		}
		if (UCommons.notEmpty(this.o.textColor)) {
			style.o.textColor = this.o.textColor;
		}
		if (UCommons.notEmpty(this.o.background)) {
			style.o.background = this.o.background;
		}
		if (UCommons.notEmpty(this.o.shadowColor)) {
			style.o.shadowColor = this.o.shadowColor;
		}
		if (UCommons.notEmpty(this.o.textShadowColor)) {
			style.o.textShadowColor = this.o.textShadowColor;
		}
		if (UCommons.notEmpty(this.o.borderColor)) {
			style.o.borderColor = this.o.borderColor;
		}
		if (UCommons.notEmpty(this.o.borderTopColor)) {
			style.o.borderTopColor = this.o.borderTopColor;
		}
		if (UCommons.notEmpty(this.o.borderBottomColor)) {
			style.o.borderBottomColor = this.o.borderBottomColor;
		}
		if (UCommons.notEmpty(this.o.borderLeftColor)) {
			style.o.borderLeftColor = this.o.borderLeftColor;
		}
		if (UCommons.notEmpty(this.o.borderRightColor)) {
			style.o.borderRightColor = this.o.borderRightColor;
		}
		if (UCommons.notEmpty(this.o.backgroundColor)) {
			style.o.backgroundColor = this.o.backgroundColor;
		}
		if (UCommons.notEmpty(this.o.textTransform)) {
			style.o.textTransform = this.o.textTransform;
		}
		if (UCommons.notEmpty(this.o.position)) {
			style.o.position = this.o.position;
		}
		if (UCommons.notEmpty(this.o.textAlign)) {
			style.o.textAlign = this.o.textAlign;
		}
		if (UCommons.notEmpty(this.o.verticalAlign)) {
			style.o.verticalAlign = this.o.verticalAlign;
		}
		if (UCommons.notEmpty(this.o.alignItems)) {
			style.o.alignItems = this.o.alignItems;
		}
		if (UCommons.notEmpty(this.o.alignSelf)) {
			style.o.alignSelf = this.o.alignSelf;
		}
		if (UCommons.notEmpty(this.o.fontWeight)) {
			style.o.fontWeight = this.o.fontWeight;
		}
		if (UCommons.notEmpty(this.o.fontStyle)) {
			style.o.fontStyle = this.o.fontStyle;
		}
		if (UCommons.notEmpty(this.o.textDecorationLine)) {
			style.o.textDecorationLine = this.o.textDecorationLine;
		}
		if (UCommons.notEmpty(this.o.borderStyle)) {
			style.o.borderStyle = this.o.borderStyle;
		}
		if (UCommons.notEmpty(this.o.borderBottomStyle)) {
			style.o.borderBottomStyle = this.o.borderBottomStyle;
		}
		if (UCommons.notEmpty(this.o.borderLeftStyle)) {
			style.o.borderLeftStyle = this.o.borderLeftStyle;
		}
		if (UCommons.notEmpty(this.o.borderTopStyle)) {
			style.o.borderTopStyle = this.o.borderTopStyle;
		}
		if (UCommons.notEmpty(this.o.borderRightStyle)) {
			style.o.borderRightStyle = this.o.borderRightStyle;
		}
		if (UCommons.notEmpty(this.o.flexDirection)) {
			style.o.flexDirection = this.o.flexDirection;
		}
		if (UCommons.notEmpty(this.o.justifyContent)) {
			style.o.justifyContent = this.o.justifyContent;
		}
		if (UCommons.notEmpty(this.o.visibility)) {
			style.o.visibility = this.o.visibility;
		}
		if (UCommons.notEmpty(this.o.float)) {
			style.o.float = this.o.float;
		}
		if (UCommons.notEmpty(this.o.overflow)) {
			style.o.overflow = this.o.overflow;
		}
		if (UCommons.notEmpty(this.o.overflowY)) {
			style.o.overflowY = this.o.overflowY;
		}
		if (UCommons.notEmpty(this.o.overflowX)) {
			style.o.overflowX = this.o.overflowX;
		}
		if (UCommons.notEmpty(this.o.display)) {
			style.o.display = this.o.display;
		}
		if (UCommons.notEmpty(this.o.flexGrow)) {
			style.o.flexGrow = this.o.flexGrow;
		}
		if (UCommons.notEmpty(this.o.left)) {
			style.o.left = this.o.left;
		}
		if (UCommons.notEmpty(this.o.right)) {
			style.o.right = this.o.right;
		}
		if (UCommons.notEmpty(this.o.lineHeight)) {
			style.o.lineHeight = this.o.lineHeight;
		}
		if (UCommons.notEmpty(this.o.cursor)) {
			style.o.cursor = this.o.cursor;
		}
		if (UCommons.notEmpty(this.o.listStyleType)) {
			style.o.listStyleType = this.o.listStyleType;
		}
		if (UCommons.notEmpty(this.o.flexWrap)) {
			style.o.flexWrap = this.o.flexWrap;
		}
		if (UCommons.notEmpty(this.o.resizeMode)) {
			style.o.resizeMode = this.o.resizeMode;
		}
		if (UCommons.notEmpty(this.o.fontFamily)) {
			style.o.fontFamily = this.o.fontFamily;
		}
		if (UCommons.notEmpty(this.o.flex)) {
			style.o.flex = this.o.flex;
		}
		if (UCommons.notEmpty(this.o.width)) {
			style.o.width = this.o.width;
		}
		if (UCommons.notEmpty(this.o.margin)) {
			style.o.margin = this.o.margin;
		}
		if (UCommons.notEmpty(this.o.minHeight)) {
			style.o.minHeight = this.o.minHeight;
		}
		if (UCommons.notEmpty(this.o.maxHeight)) {
			style.o.maxHeight = this.o.maxHeight;
		}
		if (UCommons.notEmpty(this.o.height)) {
			style.o.height = this.o.height;
		}
		if (UCommons.notEmpty(this.o.zIndex)) {
			style.o.zIndex = this.o.zIndex;
		}
		if (UCommons.notEmpty(this.o.bottom)) {
			style.o.bottom = this.o.bottom;
		}
		if (UCommons.notEmpty(this.o.minWidth)) {
			style.o.minWidth = this.o.minWidth;
		}
		if (UCommons.notEmpty(this.o.maxWidth)) {
			style.o.maxWidth = this.o.maxWidth;
		}
		if (UCommons.notEmpty(this.o.opacity)) {
			style.o.opacity = this.o.opacity;
		}
		if (UCommons.notEmpty(this.o.elevation)) {
			style.o.elevation = this.o.elevation;
		}
		if (UCommons.notEmpty(this.o.shadowRadius)) {
			style.o.shadowRadius = this.o.shadowRadius;
		}
		if (UCommons.notEmpty(this.o.textShadowRadius)) {
			style.o.textShadowRadius = this.o.textShadowRadius;
		}
		if (UCommons.notEmpty(this.o.shadowOpacity)) {
			style.o.shadowOpacity = this.o.shadowOpacity;
		}
		if (UCommons.notEmpty(this.o.textShadowOpacity)) {
			style.o.textShadowOpacity = this.o.textShadowOpacity;
		}
		if (UCommons.notEmpty(this.o.aspectRatio)) {
			style.o.aspectRatio = this.o.aspectRatio;
		}
		if (UCommons.notEmpty(this.o.fontSize)) {
			style.o.fontSize = this.o.fontSize;
		}
		if (UCommons.notEmpty(this.o.top)) {
			style.o.top = this.o.top;
		}
		if (UCommons.notEmpty(this.o.marginTop)) {
			style.o.marginTop = this.o.marginTop;
		}
		if (UCommons.notEmpty(this.o.marginLeft)) {
			style.o.marginLeft = this.o.marginLeft;
		}
		if (UCommons.notEmpty(this.o.marginRight)) {
			style.o.marginRight = this.o.marginRight;
		}
		if (UCommons.notEmpty(this.o.marginBottom)) {
			style.o.marginBottom = this.o.marginBottom;
		}
		if (UCommons.notEmpty(this.o.marginHorizontal)) {
			style.o.marginHorizontal = this.o.marginHorizontal;
		}
		if (UCommons.notEmpty(this.o.padding)) {
			style.o.padding = this.o.padding;
		}
		if (UCommons.notEmpty(this.o.paddingTop)) {
			style.o.paddingTop = this.o.paddingTop;
		}
		if (UCommons.notEmpty(this.o.paddingLeft)) {
			style.o.paddingLeft = this.o.paddingLeft;
		}
		if (UCommons.notEmpty(this.o.paddingRight)) {
			style.o.paddingRight = this.o.paddingRight;
		}
		if (UCommons.notEmpty(this.o.paddingBottom)) {
			style.o.paddingBottom = this.o.paddingBottom;
		}
		if (UCommons.notEmpty(this.o.paddingVertical)) {
			style.o.paddingVertical = this.o.paddingVertical;
		}
		if (UCommons.notEmpty(this.o.paddingHorizontal)) {
			style.o.paddingHorizontal = this.o.paddingHorizontal;
		}
		if (UCommons.notEmpty(this.o.borderWidth)) {
			style.o.borderWidth = this.o.borderWidth;
		}
		if (UCommons.notEmpty(this.o.borderLeftWidth)) {
			style.o.borderLeftWidth = this.o.borderLeftWidth;
		}
		if (UCommons.notEmpty(this.o.borderRightWidth)) {
			style.o.borderRightWidth = this.o.borderRightWidth;
		}
		if (UCommons.notEmpty(this.o.borderTopWidth)) {
			style.o.borderTopWidth = this.o.borderTopWidth;
		}
		if (UCommons.notEmpty(this.o.borderBottomWidth)) {
			style.o.borderBottomWidth = this.o.borderBottomWidth;
		}
		if (UCommons.notEmpty(this.o.borderRadius)) {
			style.o.borderRadius = this.o.borderRadius;
		}
		return style;
	}
	join(style) {
		const c = this.copy();
		if (UCommons.isEmpty(style)) {
			return c;
		}
		if (!UCommons.isEmpty(style.o.color)) {
			c.o.color = style.o.color;
		}
		if (!UCommons.isEmpty(style.o.textColor)) {
			c.o.textColor = style.o.textColor;
		}
		if (!UCommons.isEmpty(style.o.background)) {
			c.o.background = style.o.background;
		}
		if (!UCommons.isEmpty(style.o.shadowColor)) {
			c.o.shadowColor = style.o.shadowColor;
		}
		if (!UCommons.isEmpty(style.o.textShadowColor)) {
			c.o.textShadowColor = style.o.textShadowColor;
		}
		if (!UCommons.isEmpty(style.o.borderColor)) {
			c.o.borderColor = style.o.borderColor;
		}
		if (!UCommons.isEmpty(style.o.borderTopColor)) {
			c.o.borderTopColor = style.o.borderTopColor;
		}
		if (!UCommons.isEmpty(style.o.borderBottomColor)) {
			c.o.borderBottomColor = style.o.borderBottomColor;
		}
		if (!UCommons.isEmpty(style.o.borderLeftColor)) {
			c.o.borderLeftColor = style.o.borderLeftColor;
		}
		if (!UCommons.isEmpty(style.o.borderRightColor)) {
			c.o.borderRightColor = style.o.borderRightColor;
		}
		if (!UCommons.isEmpty(style.o.backgroundColor)) {
			c.o.backgroundColor = style.o.backgroundColor;
		}
		if (!UCommons.isEmpty(style.o.textTransform)) {
			c.o.textTransform = style.o.textTransform;
		}
		if (!UCommons.isEmpty(style.o.position)) {
			c.o.position = style.o.position;
		}
		if (!UCommons.isEmpty(style.o.textAlign)) {
			c.o.textAlign = style.o.textAlign;
		}
		if (!UCommons.isEmpty(style.o.verticalAlign)) {
			c.o.verticalAlign = style.o.verticalAlign;
		}
		if (!UCommons.isEmpty(style.o.alignItems)) {
			c.o.alignItems = style.o.alignItems;
		}
		if (!UCommons.isEmpty(style.o.alignSelf)) {
			c.o.alignSelf = style.o.alignSelf;
		}
		if (!UCommons.isEmpty(style.o.fontWeight)) {
			c.o.fontWeight = style.o.fontWeight;
		}
		if (!UCommons.isEmpty(style.o.fontStyle)) {
			c.o.fontStyle = style.o.fontStyle;
		}
		if (!UCommons.isEmpty(style.o.textDecorationLine)) {
			c.o.textDecorationLine = style.o.textDecorationLine;
		}
		if (!UCommons.isEmpty(style.o.borderStyle)) {
			c.o.borderStyle = style.o.borderStyle;
		}
		if (!UCommons.isEmpty(style.o.borderBottomStyle)) {
			c.o.borderBottomStyle = style.o.borderBottomStyle;
		}
		if (!UCommons.isEmpty(style.o.borderLeftStyle)) {
			c.o.borderLeftStyle = style.o.borderLeftStyle;
		}
		if (!UCommons.isEmpty(style.o.borderTopStyle)) {
			c.o.borderTopStyle = style.o.borderTopStyle;
		}
		if (!UCommons.isEmpty(style.o.borderRightStyle)) {
			c.o.borderRightStyle = style.o.borderRightStyle;
		}
		if (!UCommons.isEmpty(style.o.flexDirection)) {
			c.o.flexDirection = style.o.flexDirection;
		}
		if (!UCommons.isEmpty(style.o.justifyContent)) {
			c.o.justifyContent = style.o.justifyContent;
		}
		if (!UCommons.isEmpty(style.o.visibility)) {
			c.o.visibility = style.o.visibility;
		}
		if (!UCommons.isEmpty(style.o.float)) {
			c.o.float = style.o.float;
		}
		if (!UCommons.isEmpty(style.o.overflow)) {
			c.o.overflow = style.o.overflow;
		}
		if (!UCommons.isEmpty(style.o.overflowY)) {
			c.o.overflowY = style.o.overflowY;
		}
		if (!UCommons.isEmpty(style.o.overflowX)) {
			c.o.overflowX = style.o.overflowX;
		}
		if (!UCommons.isEmpty(style.o.display)) {
			c.o.display = style.o.display;
		}
		if (!UCommons.isEmpty(style.o.flexGrow)) {
			c.o.flexGrow = style.o.flexGrow;
		}
		if (!UCommons.isEmpty(style.o.left)) {
			c.o.left = style.o.left;
		}
		if (!UCommons.isEmpty(style.o.right)) {
			c.o.right = style.o.right;
		}
		if (!UCommons.isEmpty(style.o.lineHeight)) {
			c.o.lineHeight = style.o.lineHeight;
		}
		if (!UCommons.isEmpty(style.o.cursor)) {
			c.o.cursor = style.o.cursor;
		}
		if (!UCommons.isEmpty(style.o.listStyleType)) {
			c.o.listStyleType = style.o.listStyleType;
		}
		if (!UCommons.isEmpty(style.o.flexWrap)) {
			c.o.flexWrap = style.o.flexWrap;
		}
		if (!UCommons.isEmpty(style.o.resizeMode)) {
			c.o.resizeMode = style.o.resizeMode;
		}
		if (!UCommons.isEmpty(style.o.fontFamily)) {
			c.o.fontFamily = style.o.fontFamily;
		}
		if (!UCommons.isEmpty(style.o.flex)) {
			c.o.flex = style.o.flex;
		}
		if (!UCommons.isEmpty(style.o.width)) {
			c.o.width = style.o.width;
		}
		if (!UCommons.isEmpty(style.o.margin)) {
			c.o.margin = style.o.margin;
		}
		if (!UCommons.isEmpty(style.o.minHeight)) {
			c.o.minHeight = style.o.minHeight;
		}
		if (!UCommons.isEmpty(style.o.maxHeight)) {
			c.o.maxHeight = style.o.maxHeight;
		}
		if (!UCommons.isEmpty(style.o.height)) {
			c.o.height = style.o.height;
		}
		if (!UCommons.isEmpty(style.o.zIndex)) {
			c.o.zIndex = style.o.zIndex;
		}
		if (!UCommons.isEmpty(style.o.bottom)) {
			c.o.bottom = style.o.bottom;
		}
		if (!UCommons.isEmpty(style.o.minWidth)) {
			c.o.minWidth = style.o.minWidth;
		}
		if (!UCommons.isEmpty(style.o.maxWidth)) {
			c.o.maxWidth = style.o.maxWidth;
		}
		if (!UCommons.isEmpty(style.o.opacity)) {
			c.o.opacity = style.o.opacity;
		}
		if (!UCommons.isEmpty(style.o.elevation)) {
			c.o.elevation = style.o.elevation;
		}
		if (!UCommons.isEmpty(style.o.shadowRadius)) {
			c.o.shadowRadius = style.o.shadowRadius;
		}
		if (!UCommons.isEmpty(style.o.textShadowRadius)) {
			c.o.textShadowRadius = style.o.textShadowRadius;
		}
		if (!UCommons.isEmpty(style.o.shadowOpacity)) {
			c.o.shadowOpacity = style.o.shadowOpacity;
		}
		if (!UCommons.isEmpty(style.o.textShadowOpacity)) {
			c.o.textShadowOpacity = style.o.textShadowOpacity;
		}
		if (!UCommons.isEmpty(style.o.aspectRatio)) {
			c.o.aspectRatio = style.o.aspectRatio;
		}
		if (!UCommons.isEmpty(style.o.fontSize)) {
			c.o.fontSize = style.o.fontSize;
		}
		if (!UCommons.isEmpty(style.o.top)) {
			c.o.top = style.o.top;
		}
		if (!UCommons.isEmpty(style.o.marginTop)) {
			c.o.marginTop = style.o.marginTop;
		}
		if (!UCommons.isEmpty(style.o.marginLeft)) {
			c.o.marginLeft = style.o.marginLeft;
		}
		if (!UCommons.isEmpty(style.o.marginRight)) {
			c.o.marginRight = style.o.marginRight;
		}
		if (!UCommons.isEmpty(style.o.marginBottom)) {
			c.o.marginBottom = style.o.marginBottom;
		}
		if (!UCommons.isEmpty(style.o.marginHorizontal)) {
			c.o.marginHorizontal = style.o.marginHorizontal;
		}
		if (!UCommons.isEmpty(style.o.padding)) {
			c.o.padding = style.o.padding;
		}
		if (!UCommons.isEmpty(style.o.paddingTop)) {
			c.o.paddingTop = style.o.paddingTop;
		}
		if (!UCommons.isEmpty(style.o.paddingLeft)) {
			c.o.paddingLeft = style.o.paddingLeft;
		}
		if (!UCommons.isEmpty(style.o.paddingRight)) {
			c.o.paddingRight = style.o.paddingRight;
		}
		if (!UCommons.isEmpty(style.o.paddingBottom)) {
			c.o.paddingBottom = style.o.paddingBottom;
		}
		if (!UCommons.isEmpty(style.o.paddingVertical)) {
			c.o.paddingVertical = style.o.paddingVertical;
		}
		if (!UCommons.isEmpty(style.o.paddingHorizontal)) {
			c.o.paddingHorizontal = style.o.paddingHorizontal;
		}
		if (!UCommons.isEmpty(style.o.borderWidth)) {
			c.o.borderWidth = style.o.borderWidth;
		}
		if (!UCommons.isEmpty(style.o.borderLeftWidth)) {
			c.o.borderLeftWidth = style.o.borderLeftWidth;
		}
		if (!UCommons.isEmpty(style.o.borderRightWidth)) {
			c.o.borderRightWidth = style.o.borderRightWidth;
		}
		if (!UCommons.isEmpty(style.o.borderTopWidth)) {
			c.o.borderTopWidth = style.o.borderTopWidth;
		}
		if (!UCommons.isEmpty(style.o.borderBottomWidth)) {
			c.o.borderBottomWidth = style.o.borderBottomWidth;
		}
		if (!UCommons.isEmpty(style.o.borderRadius)) {
			c.o.borderRadius = style.o.borderRadius;
		}
		return c;
	}
	colorClear() {
		this.checkLock();
		this.o.color = undefined;
		return this;
	}
	textColorClear() {
		this.checkLock();
		this.o.textColor = undefined;
		return this;
	}
	backgroundClear() {
		this.checkLock();
		this.o.background = undefined;
		return this;
	}
	shadowColorClear() {
		this.checkLock();
		this.o.shadowColor = undefined;
		return this;
	}
	textShadowColorClear() {
		this.checkLock();
		this.o.textShadowColor = undefined;
		return this;
	}
	borderColorClear() {
		this.checkLock();
		this.o.borderColor = undefined;
		return this;
	}
	borderTopColorClear() {
		this.checkLock();
		this.o.borderTopColor = undefined;
		return this;
	}
	borderBottomColorClear() {
		this.checkLock();
		this.o.borderBottomColor = undefined;
		return this;
	}
	borderLeftColorClear() {
		this.checkLock();
		this.o.borderLeftColor = undefined;
		return this;
	}
	borderRightColorClear() {
		this.checkLock();
		this.o.borderRightColor = undefined;
		return this;
	}
	backgroundColorClear() {
		this.checkLock();
		this.o.backgroundColor = undefined;
		return this;
	}
	textTransformClear() {
		this.checkLock();
		this.o.textTransform = undefined;
		return this;
	}
	positionClear() {
		this.checkLock();
		this.o.position = undefined;
		return this;
	}
	textAlignClear() {
		this.checkLock();
		this.o.textAlign = undefined;
		return this;
	}
	verticalAlignClear() {
		this.checkLock();
		this.o.verticalAlign = undefined;
		return this;
	}
	alignItemsClear() {
		this.checkLock();
		this.o.alignItems = undefined;
		return this;
	}
	alignSelfClear() {
		this.checkLock();
		this.o.alignSelf = undefined;
		return this;
	}
	fontWeightClear() {
		this.checkLock();
		this.o.fontWeight = undefined;
		return this;
	}
	fontStyleClear() {
		this.checkLock();
		this.o.fontStyle = undefined;
		return this;
	}
	textDecorationLineClear() {
		this.checkLock();
		this.o.textDecorationLine = undefined;
		return this;
	}
	borderStyleClear() {
		this.checkLock();
		this.o.borderStyle = undefined;
		return this;
	}
	borderBottomStyleClear() {
		this.checkLock();
		this.o.borderBottomStyle = undefined;
		return this;
	}
	borderLeftStyleClear() {
		this.checkLock();
		this.o.borderLeftStyle = undefined;
		return this;
	}
	borderTopStyleClear() {
		this.checkLock();
		this.o.borderTopStyle = undefined;
		return this;
	}
	borderRightStyleClear() {
		this.checkLock();
		this.o.borderRightStyle = undefined;
		return this;
	}
	flexDirectionClear() {
		this.checkLock();
		this.o.flexDirection = undefined;
		return this;
	}
	justifyContentClear() {
		this.checkLock();
		this.o.justifyContent = undefined;
		return this;
	}
	visibilityClear() {
		this.checkLock();
		this.o.visibility = undefined;
		return this;
	}
	floatClear() {
		this.checkLock();
		this.o.float = undefined;
		return this;
	}
	overflowClear() {
		this.checkLock();
		this.o.overflow = undefined;
		return this;
	}
	overflowYClear() {
		this.checkLock();
		this.o.overflowY = undefined;
		return this;
	}
	overflowXClear() {
		this.checkLock();
		this.o.overflowX = undefined;
		return this;
	}
	displayClear() {
		this.checkLock();
		this.o.display = undefined;
		return this;
	}
	flexGrowClear() {
		this.checkLock();
		this.o.flexGrow = undefined;
		return this;
	}
	leftClear() {
		this.checkLock();
		this.o.left = undefined;
		return this;
	}
	rightClear() {
		this.checkLock();
		this.o.right = undefined;
		return this;
	}
	lineHeightClear() {
		this.checkLock();
		this.o.lineHeight = undefined;
		return this;
	}
	cursorClear() {
		this.checkLock();
		this.o.cursor = undefined;
		return this;
	}
	listStyleTypeClear() {
		this.checkLock();
		this.o.listStyleType = undefined;
		return this;
	}
	flexWrapClear() {
		this.checkLock();
		this.o.flexWrap = undefined;
		return this;
	}
	resizeModeClear() {
		this.checkLock();
		this.o.resizeMode = undefined;
		return this;
	}
	fontFamilyClear() {
		this.checkLock();
		this.o.fontFamily = undefined;
		return this;
	}
	flexClear() {
		this.checkLock();
		this.o.flex = undefined;
		return this;
	}
	widthClear() {
		this.checkLock();
		this.o.width = undefined;
		return this;
	}
	marginClear() {
		this.checkLock();
		this.o.margin = undefined;
		return this;
	}
	minHeightClear() {
		this.checkLock();
		this.o.minHeight = undefined;
		return this;
	}
	maxHeightClear() {
		this.checkLock();
		this.o.maxHeight = undefined;
		return this;
	}
	heightClear() {
		this.checkLock();
		this.o.height = undefined;
		return this;
	}
	zIndexClear() {
		this.checkLock();
		this.o.zIndex = undefined;
		return this;
	}
	bottomClear() {
		this.checkLock();
		this.o.bottom = undefined;
		return this;
	}
	minWidthClear() {
		this.checkLock();
		this.o.minWidth = undefined;
		return this;
	}
	maxWidthClear() {
		this.checkLock();
		this.o.maxWidth = undefined;
		return this;
	}
	opacityClear() {
		this.checkLock();
		this.o.opacity = undefined;
		return this;
	}
	elevationClear() {
		this.checkLock();
		this.o.elevation = undefined;
		return this;
	}
	shadowRadiusClear() {
		this.checkLock();
		this.o.shadowRadius = undefined;
		return this;
	}
	textShadowRadiusClear() {
		this.checkLock();
		this.o.textShadowRadius = undefined;
		return this;
	}
	shadowOpacityClear() {
		this.checkLock();
		this.o.shadowOpacity = undefined;
		return this;
	}
	textShadowOpacityClear() {
		this.checkLock();
		this.o.textShadowOpacity = undefined;
		return this;
	}
	aspectRatioClear() {
		this.checkLock();
		this.o.aspectRatio = undefined;
		return this;
	}
	fontSizeClear() {
		this.checkLock();
		this.o.fontSize = undefined;
		return this;
	}
	topClear() {
		this.checkLock();
		this.o.top = undefined;
		return this;
	}
	marginTopClear() {
		this.checkLock();
		this.o.marginTop = undefined;
		return this;
	}
	marginLeftClear() {
		this.checkLock();
		this.o.marginLeft = undefined;
		return this;
	}
	marginRightClear() {
		this.checkLock();
		this.o.marginRight = undefined;
		return this;
	}
	marginBottomClear() {
		this.checkLock();
		this.o.marginBottom = undefined;
		return this;
	}
	marginHorizontalClear() {
		this.checkLock();
		this.o.marginHorizontal = undefined;
		return this;
	}
	paddingClear() {
		this.checkLock();
		this.o.padding = undefined;
		return this;
	}
	paddingTopClear() {
		this.checkLock();
		this.o.paddingTop = undefined;
		return this;
	}
	paddingLeftClear() {
		this.checkLock();
		this.o.paddingLeft = undefined;
		return this;
	}
	paddingRightClear() {
		this.checkLock();
		this.o.paddingRight = undefined;
		return this;
	}
	paddingBottomClear() {
		this.checkLock();
		this.o.paddingBottom = undefined;
		return this;
	}
	paddingVerticalClear() {
		this.checkLock();
		this.o.paddingVertical = undefined;
		return this;
	}
	paddingHorizontalClear() {
		this.checkLock();
		this.o.paddingHorizontal = undefined;
		return this;
	}
	borderWidthClear() {
		this.checkLock();
		this.o.borderWidth = undefined;
		return this;
	}
	borderLeftWidthClear() {
		this.checkLock();
		this.o.borderLeftWidth = undefined;
		return this;
	}
	borderRightWidthClear() {
		this.checkLock();
		this.o.borderRightWidth = undefined;
		return this;
	}
	borderTopWidthClear() {
		this.checkLock();
		this.o.borderTopWidth = undefined;
		return this;
	}
	borderBottomWidthClear() {
		this.checkLock();
		this.o.borderBottomWidth = undefined;
		return this;
	}
	borderRadiusClear() {
		this.checkLock();
		this.o.borderRadius = undefined;
		return this;
	}
}
