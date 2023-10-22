package comfst;

import javax.servlet.http.HttpServlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListeEmail extends HttpServlet{
	private String chemin="C:/Users/Zikoo/OneDrive/Bureau/mail.txt";
	private List <String> listMail=new ArrayList();
	private String erreur;
	
	//constructeur
	public ListeEmail() {
		super();
	}
	
	public void init() {
		try {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(chemin)); 
		listMail=(List <String>)in.readObject();
		in.close();
		}
		catch(Exception e ) {
			erreur=e.toString();
		}
		
	}
	
	private void save(List<String> addresses) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(chemin))) {
	        for (String address : addresses) {
	            writer.write(address);
	            writer.newLine(); // Pour passer à la ligne suivante
	        }
	    }
	}

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	  
		//this.save("ppppp");
		PrintWriter out = res.getWriter();
	    res.setContentType("text/html");
        out.println("<HTML>");
        out.println("<head><title>gerer mail</title></head>");
        out.println("<body>");
        out.println("<h1>Membres: </h1>");
        out.println("<br>");
        out.println("<br>");
        out.println("<ul>");

	    for (int i=0 ; i < listMail.size();i++) {
	    	String element = listMail.get(i);
	    	out.println("<li>"+element+"</li>");
	    }
        out.println("</ul>");
        out.println("<hr>");
        out.println("<p>Entrer votre adresse email: </p><form method='POST'>");
        out.println("<input type='email' name='email' placeholder='Votre adresse e-mail' required>");
        out.println("<input type='submit' name='action' value='subscribe'>"); // Bouton "subscribe"
        out.println("<input type='submit' name='action' value='unsubscribe'>"); // Bouton "unsubscribe"
        out.println("</body>");
        out.println("</HTML>");

    
	    
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException{
		String email= req.getParameter("email");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		out.println("<HTML>");
        out.println("<head><title>Gerer mail</title></head>");
        out.println("<body>");
        out.println("<h1>Membres: </h1>");
        out.println("<hr>");
        out.println("<a href='ListeEmail'>Retourner a la liste</a>");
        out.println("<hr>");
        out.println("</body>");

        if ("subscribe".equals(action)) {
            // Logique pour ajouter l'adresse e-mail à la liste
            listMail.add(email);
            save(listMail); // Enregistrer la liste dans le fichier
            out.println("l adresse email '"+email+"'  "+"est ajoutee avec succes");
            // ...
        } else if ("unsubscribe".equals(action)) {
            // Logique pour supprimer l'adresse e-mail de la liste
            listMail.remove(email);
            save(listMail); // Enregistrer la liste dans le fichier
            out.println("l adresse email '"+email+"'  "+"est supprimer avec succes");
            // ...
        }
	}
	
}