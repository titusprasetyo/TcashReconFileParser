/*
 * ZKoss Controller Class for Upload UI
 * Dev by : Titus Adi Prasetyo
 * 
 */

package com.agit.tcash.recon.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import com.agit.tcash.recon.dao.ParseDao;
import com.agit.tcash.recon.model.UploadLog;
import com.agit.tcash.recon.parseworker.Parser;
import com.agit.tcash.recon.util.UploadUtils;

public class UploadFileController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uploadedFilesDir = "D:\\EclipseWorkspace\\";
	private String filename = "";
	private String fullpath = "";

	@Wire
	private Label message;

	@Listen("onUpload = #btnUpload")
	public void upload(UploadEvent evt) {
		String uuid = UploadUtils.generateUUID();
		Parser parser = null;
		UploadLog log = null;
		message.setValue(null);
		ParseDao dao = null;
		Media media = evt.getMedia();
		if ((media != null) && media.getName().toLowerCase().endsWith(".txt")) {
			filename = media.getName();
			fullpath = uploadedFilesDir + filename;
			FileOutputStream fop = null;
			File file;
			String content = media.getStringData();
			try {
				file = new File(fullpath);
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
				fop.close();

				message.setValue("File saved to " + fullpath + "\n");
				// save upload log table
				try {
					
					dao = new ParseDao();
					if (dao.isParsed(UploadUtils.getChecksumString(fullpath))){
						message.setValue("File already parsed before\n");
						return;
					}
					log = new UploadLog();
					log.setBatchID(uuid);
					log.setFilename(FilenameUtils.getName(fullpath));
					log.setChecksum(UploadUtils.getChecksumString(fullpath));
					log.setDtStamp();
					dao.saveUploadLog(log);
					message.setValue("Saved to upload log table\n");
					try {
						message.setValue(message.getValue() + "Start parsing file\n");
						parser = new Parser();
						message.setValue(message.getValue() + parser.doParse(fullpath,uuid) + "\n");
					} catch (Exception e) {
						message.setValue(message.getValue() + "Error Parsing file " + e.getMessage());
					}
				} catch (Exception e) {
					message.setValue(message.getValue() + "Error saving to log table " + e.getMessage());
				}

			} catch (Exception e) {
				// e.printStackTrace();
				message.setValue("Error writing file " + e.getMessage());
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					message.setValue("Error writing file " + e.getMessage());
				}
			}
		} else {
			message.setValue("File empty or not txt file");
		}
	}
}
