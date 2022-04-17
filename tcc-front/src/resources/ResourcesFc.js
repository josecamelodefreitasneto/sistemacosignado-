/* front-constructor */
import download_png from './download.png';
import pdf_ico from './pdf.ico';
import user_png from './user.png';
import xls_png from './xls.png';

export default class ResourcesFc {}
ResourcesFc.download = download_png;
ResourcesFc.pdf = pdf_ico;
ResourcesFc.user = user_png;
ResourcesFc.xls = xls_png;
ResourcesFc.map =
		new Map()
		.set("download", ResourcesFc.download)
		.set("pdf", ResourcesFc.pdf)
		.set("user", ResourcesFc.user)
		.set("xls", ResourcesFc.xls)
		;
ResourcesFc.list = [
	ResourcesFc.download
	, ResourcesFc.pdf
	, ResourcesFc.user
	, ResourcesFc.xls
];
