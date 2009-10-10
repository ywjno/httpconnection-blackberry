interface HttpUrlTextReceiver {
	public void setUrlText(Object userData, String url, String text, boolean errFlag, String errMsg); 
	public void allUrlsFetched();
	public void unforeSeenException(String err);
};
