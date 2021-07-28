package com.example.lucifer.myappchiatiennuoc;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class capnhatdongia extends Fragment implements View.OnClickListener {

    EditText txtDonGiaDM, txtDonGiaNgoaiDinhMuc, txtThue, txtPhi, txtDM_Motnguoi, txtSoNguoi;
    Button btnUpdate;

    final String STING_PATH =Environment.getDataDirectory().getPath() + "/data/com.example.lucifer.myappchiatiennuoc/";
    final String STING_FILENAME="DonGiaNuoc.xml";
    File fileXML = new File(STING_PATH + STING_FILENAME);

    DocumentBuilderFactory docBuilderFactory;
    DocumentBuilder docBuilder;

    Document docXMLDonGia;

    DecimalTextWatcher[] decimalTextWatchers = new DecimalTextWatcher[6];


    public capnhatdongia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.capnhatdongia_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtDonGiaDM=(EditText)view.findViewById(R.id.editTextDonGiaDM);
        decimalTextWatchers[0] = new DecimalTextWatcher(txtDonGiaDM);
        txtDonGiaDM.addTextChangedListener(decimalTextWatchers[0]);

        txtDonGiaNgoaiDinhMuc=(EditText)view.findViewById(R.id.editTextDonGiaNuocNgoaiDinhMuc);
        decimalTextWatchers[1] = new DecimalTextWatcher(txtDonGiaNgoaiDinhMuc);
        txtDonGiaNgoaiDinhMuc.addTextChangedListener(decimalTextWatchers[1]);

        txtThue=(EditText)view.findViewById(R.id.editTextThue);
        decimalTextWatchers[2] = new DecimalTextWatcher(txtThue);
        txtThue.addTextChangedListener(decimalTextWatchers[2]);

        txtPhi=(EditText)view.findViewById(R.id.editTextPhi);
        decimalTextWatchers[3] = new DecimalTextWatcher(txtPhi);
        txtPhi.addTextChangedListener(decimalTextWatchers[3]);

        txtDM_Motnguoi=(EditText)view.findViewById(R.id.editTextDinhMucKhoi);
        decimalTextWatchers[4] = new DecimalTextWatcher(txtDM_Motnguoi);
        txtDM_Motnguoi.addTextChangedListener(decimalTextWatchers[4]);

        txtSoNguoi=(EditText)view.findViewById(R.id.editTextSoNguoiTrongDinhMuc);
        decimalTextWatchers[5] = new DecimalTextWatcher(txtSoNguoi);
        txtSoNguoi.addTextChangedListener(decimalTextWatchers[5]);

        btnUpdate=(Button)view.findViewById(R.id.buttonUpdate);

        btnUpdate.setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);

        if (!fileXML.exists()) {
            return;
        }else{
            readFileDonGiaXML();
        }

    }

    private void readFileDonGiaXML() {
        String text = "";
        DecimalFormat df = new DecimalFormat("#,###");

        try{
            XmlPullParserFactory xmlPPF = XmlPullParserFactory.newInstance();
            //xmlPPF.setNamespaceAware(true);

            XmlPullParser xmlPP = xmlPPF.newPullParser();

            FileInputStream inputFileXML = new FileInputStream(fileXML);

            xmlPP.setInput(inputFileXML,null);

            int eventType = xmlPP.getEventType();

            while (eventType!=XmlPullParser.END_DOCUMENT) {
                String tagname = xmlPP.getName();

                switch (eventType) {
                    case XmlPullParser.TEXT:
                        text = xmlPP.getText();
                        text=text.replaceAll(",","");
                        break;

                    case XmlPullParser.END_TAG:

                        try{
                            text = df.format(Integer.parseInt(text));
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }

                        if (tagname.equalsIgnoreCase("DonGiaDinhMuc")) {
                            txtDonGiaDM.setText(text);
                            text="";
                        } else if (tagname.equalsIgnoreCase("DonGiaNgoaiDinhMuc")) {
                            txtDonGiaNgoaiDinhMuc.setText(text);
                            text="";
                        } else if (tagname.equalsIgnoreCase("DonGiaNgoaiDinhMuc")) {
                            txtDonGiaNgoaiDinhMuc.setText(text);
                            text="";
                        } else if (tagname.equalsIgnoreCase("Thue")) {
                            txtThue.setText(text);
                            text="";
                        } else if (tagname.equalsIgnoreCase("Phi")) {
                            txtPhi.setText(text);
                            text="";
                        } else if (tagname.equalsIgnoreCase("DMCaNhan")) {
                            txtDM_Motnguoi.setText(text);
                            text="";
                        } else if (tagname.equalsIgnoreCase("SoNguoiGiaDinhDM")) {
                            txtSoNguoi.setText(text);
                            text="";
                        }
                        break;

                    default:break;
                }
                eventType=xmlPP.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

        try {
            //nếu file chưa tồn tại
            if (!fileXML.exists()) {
                //tạo file mới
                fileXML.createNewFile();

                //ghi dữ liệu ra file
                writeNewFileDonGiaXML();

                //thoát luôn
                return;
            }

            //nếu file đã tồn tại thì update dữ liệu
            docBuilderFactory = DocumentBuilderFactory.newInstance();

            docBuilder = docBuilderFactory.newDocumentBuilder();

            docXMLDonGia = docBuilder.parse(fileXML);

            Node nodeData = docXMLDonGia.getFirstChild();

            String[] dataCollection = new String[6];

            dataCollection[0]=txtDonGiaDM.getText().toString().replaceAll(",","");
            dataCollection[1]=txtDonGiaNgoaiDinhMuc.getText().toString().replaceAll(",","");
            dataCollection[2]=txtThue.getText().toString().replaceAll(",","");
            dataCollection[3]=txtPhi.getText().toString().replaceAll(",","");
            dataCollection[4]=txtDM_Motnguoi.getText().toString().replaceAll(",","");
            dataCollection[5]=txtSoNguoi.getText().toString().replaceAll(",","");

            int i;

            for (i=0;i<=5;i++) {
                NodeList nodeList = (NodeList)nodeData.getChildNodes();
                Element nodeItem = (Element)nodeList.item(i);
                nodeItem.setTextContent(dataCollection[i]);
            }

            TransformerFactory transformerFactory =  TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource DomSource = new DOMSource(docXMLDonGia);

            StreamResult streamResult = new StreamResult(fileXML);

            transformer.transform(DomSource,streamResult);

            Toast toast = Toast.makeText(getActivity(),"Đã ghi dữ liệu ra file", Toast.LENGTH_SHORT);
            toast.show();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private void writeNewFileDonGiaXML() {
        try {
            docBuilderFactory = DocumentBuilderFactory.newInstance();

            docBuilder = docBuilderFactory.newDocumentBuilder();

            docXMLDonGia = docBuilder.newDocument();

            //docXMLDonGia = docBuilder.parse(fileXML);

            //--------------------------------------------------------------------------------------
            Element root = docXMLDonGia.createElement("Data");

            docXMLDonGia.appendChild(root);

            //--------------------------------------------------------------------------------------
            Element gia_DinhMuc = docXMLDonGia.createElement("DonGiaDinhMuc");

            gia_DinhMuc.appendChild(docXMLDonGia.createTextNode(txtDonGiaDM.getText().toString()));

            root.appendChild(gia_DinhMuc);

            //--------------------------------------------------------------------------------------
            Element gia_NgoaiDinhMuc = docXMLDonGia.createElement("DonGiaNgoaiDinhMuc");

            gia_NgoaiDinhMuc.appendChild(docXMLDonGia.createTextNode(txtDonGiaNgoaiDinhMuc.getText().toString()));

            root.appendChild(gia_NgoaiDinhMuc);

            //--------------------------------------------------------------------------------------
            Element thue = docXMLDonGia.createElement("Thue");

            thue.appendChild(docXMLDonGia.createTextNode(txtThue.getText().toString()));

            root.appendChild(thue);

            //--------------------------------------------------------------------------------------
            Element phi = docXMLDonGia.createElement("Phi");

            phi.appendChild(docXMLDonGia.createTextNode(txtPhi.getText().toString()));

            root.appendChild(phi);

            //--------------------------------------------------------------------------------------
            Element dm_mot_nguoi = docXMLDonGia.createElement("DMCaNhan");

            dm_mot_nguoi.appendChild(docXMLDonGia.createTextNode(txtDM_Motnguoi.getText().toString()));

            root.appendChild(dm_mot_nguoi);

            //--------------------------------------------------------------------------------------
            Element so_nguoi_DM = docXMLDonGia.createElement("SoNguoiGiaDinhDM");

            so_nguoi_DM.appendChild(docXMLDonGia.createTextNode(txtSoNguoi.getText().toString()));

            root.appendChild(so_nguoi_DM);

            TransformerFactory transformerFactory =  TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource DomSource = new DOMSource(docXMLDonGia);

            StreamResult streamResult = new StreamResult(fileXML);

            transformer.transform(DomSource,streamResult);

            Toast toast = Toast.makeText(getActivity(),"Đã ghi dữ liệu ra file", Toast.LENGTH_SHORT);

        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

}

