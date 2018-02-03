/**
 * 
 */
package com.ydcun.libsvm_action.model;


public class Item {
	private String act;
	private String position;
	private Double t_min;
	private Double t_max;
	private Double t_mcr;
	private Double t_sttdev;
	private Double t_mean;
	private Double t_rms;
	private Double t_iqr;
	private Double t_mad;
	private Double t_variance;
	
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(String act, String position, Double t_min, Double t_max, Double t_mcr, Double t_sttdev, Double t_mean,
			Double t_rms, Double t_iqr, Double t_mad, Double t_variance) {
		super();
		this.act = act;
		this.position = position;
		this.t_min = t_min;
		this.t_max = t_max;
		this.t_mcr = t_mcr;
		this.t_sttdev = t_sttdev;
		this.t_mean = t_mean;
		this.t_rms = t_rms;
		this.t_iqr = t_iqr;
		this.t_mad = t_mad;
		this.t_variance = t_variance;
	}

	/**  
	 * get act  
	 * @return act act  
	 */
	public String getAct() {
		return act;
	}
	
	/**  
	 * set act  
	 * @param act act  
	 */
	public void setAct(String act) {
		this.act = act;
	}
	
	/**  
	 * get position  
	 * @return position position  
	 */
	public String getPosition() {
		return position;
	}
	
	/**  
	 * set position  
	 * @param position position  
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**  
	 * get t_min  
	 * @return t_min t_min  
	 */
	public Double getT_min() {
		return t_min;
	}
	
	/**  
	 * set t_min  
	 * @param t_min t_min  
	 */
	public void setT_min(Double t_min) {
		this.t_min = t_min;
	}
	
	/**  
	 * get t_max  
	 * @return t_max t_max  
	 */
	public Double getT_max() {
		return t_max;
	}
	
	/**  
	 * set t_max  
	 * @param t_max t_max  
	 */
	public void setT_max(Double t_max) {
		this.t_max = t_max;
	}
	
	/**  
	 * get t_mcr  
	 * @return t_mcr t_mcr  
	 */
	public Double getT_mcr() {
		return t_mcr;
	}
	
	/**  
	 * set t_mcr  
	 * @param t_mcr t_mcr  
	 */
	public void setT_mcr(Double t_mcr) {
		this.t_mcr = t_mcr;
	}
	
	/**  
	 * get t_sttdev  
	 * @return t_sttdev t_sttdev  
	 */
	public Double getT_sttdev() {
		return t_sttdev;
	}
	
	/**  
	 * set t_sttdev  
	 * @param t_sttdev t_sttdev  
	 */
	public void setT_sttdev(Double t_sttdev) {
		this.t_sttdev = t_sttdev;
	}
	
	/**  
	 * get t_mean  
	 * @return t_mean t_mean  
	 */
	public Double getT_mean() {
		return t_mean;
	}
	
	/**  
	 * set t_mean  
	 * @param t_mean t_mean  
	 */
	public void setT_mean(Double t_mean) {
		this.t_mean = t_mean;
	}
	
	/**  
	 * get t_rms  
	 * @return t_rms t_rms  
	 */
	public Double getT_rms() {
		return t_rms;
	}
	
	/**  
	 * set t_rms  
	 * @param t_rms t_rms  
	 */
	public void setT_rms(Double t_rms) {
		this.t_rms = t_rms;
	}
	
	/**  
	 * get t_iqr  
	 * @return t_iqr t_iqr  
	 */
	public Double getT_iqr() {
		return t_iqr;
	}
	
	/**  
	 * set t_iqr  
	 * @param t_iqr t_iqr  
	 */
	public void setT_iqr(Double t_iqr) {
		this.t_iqr = t_iqr;
	}
	
	/**  
	 * get t_mad  
	 * @return t_mad t_mad  
	 */
	public Double getT_mad() {
		return t_mad;
	}
	
	/**  
	 * set t_mad  
	 * @param t_mad t_mad  
	 */
	public void setT_mad(Double t_mad) {
		this.t_mad = t_mad;
	}
	
	/**  
	 * get t_variance  
	 * @return t_variance t_variance  
	 */
	public Double getT_variance() {
		return t_variance;
	}
	
	/**  
	 * set t_variance  
	 * @param t_variance t_variance  
	 */
	public void setT_variance(Double t_variance) {
		this.t_variance = t_variance;
	}
	
	
	
}
