package com.servicenow.prhawk.core;

public class LastPageLink {
	private static final String DELIM_LINKS = ",";

	private static final String DELIM_LINK_PARAM = ";"; 

	private static final String META_REL = "rel";
	
	private static final String META_LAST = "last";
	
	private static final String DELIM_Page = "\\?";
	
	private static final String DELIM_Page_PARAM = "&";
	
	private static final String META_PAGE = "page";
	
	private String url;
	private int page;
	public LastPageLink(String linkHeader) {
		if (linkHeader != null) {
			String[] links = linkHeader.split(DELIM_LINKS);
			for (String link : links) {
				String[] segments = link.split(DELIM_LINK_PARAM);
				if (segments.length < 2)
					continue;

				String linkPart = segments[0].trim();
				if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) 
					continue;
				linkPart = linkPart.substring(1, linkPart.length() - 1);
				
				for (int i = 1; i < segments.length; i++) {
					String[] rel = segments[i].trim().split("=");
					if (rel.length < 2 || !META_REL.equals(rel[0]))
						continue;

					String relValue = rel[1];
					if (relValue.startsWith("\"") && relValue.endsWith("\""))
						relValue = relValue.substring(1, relValue.length() - 1);

					if (META_LAST.equals(relValue)) {
						url = linkPart;
						break;
					}
				}
			}
			if(url != null && !url.isEmpty()) {
				String[] pageInfos = url.split(DELIM_Page);
				if(pageInfos.length == 2) {
					String pageDetail = pageInfos[1].trim();
					String[] pageDetailInfos = pageDetail.split(DELIM_Page_PARAM);
					for (int i = 1; i < pageDetailInfos.length; i++) {
						String[] pageItem = pageDetailInfos[i].trim().split("=");
						if (pageItem.length == 2 && pageItem[0].equals(META_PAGE)) {
							String val = pageItem[1];
							try {
								page = Integer.parseInt(val);
							} catch(NumberFormatException e) {
								//not handler here now
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * @return last page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return last page url
	 */
	public String getUrl() {
		return url;
	}

}
