package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDao {
	public static void main(String[] args) {
		BookDao dao = new BookDao();
		//dao.insert(15, "해리포터", "롤링롤링", "N");
		//dao.delete(10);
		//dao.update(10);
		List<Book> list = dao.getList();
		
		System.out.println("책 리스트 =====================");
		for(Book book : list) {
			System.out.println(book.toString());
		}
	}
	
	// 데이터 삽입
	public void insert(int no, String title, String author, String isRent) {
		try {
			Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "insert into book values (" + no +", '"+title+"', '"+ author + "', '" + isRent + "', sysdate, null)";
			// 삽입/수정/삭제
			int res = stmt.executeUpdate(sql);
			
			System.out.println( res + "건 처리되었습니다.");
			// 자원반납
			DBUtil.closeConnection(conn, stmt);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 데이터 수정
	public void update(int no) {
		
		try {
			Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
			
			int res = stmt.executeUpdate("update book set book.title = '안녕하세요' where no = " + no);
			System.out.println(res + "건 수정되었습니다.");
			
			System.out.println("대기");
			Thread.sleep(5000);
			
			DBUtil.closeConnection(conn, stmt);
			System.out.println("자원반납");
			System.out.println("대기");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// 데이터 삭제
	public void delete(int no) {
		try {
			Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
			String sql ="delete from book where no = " + no;
			int res = stmt.executeUpdate(sql);
			System.out.println(res + "건 처리되었습니다");
			DBUtil.closeConnection(conn, stmt);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 데이터 조회
	public List<Book> getList(){
		List<Book> list = new ArrayList<>();
	
		try {
			Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
			// 질의결과를 결과집합으로 받아 옵니다.
			ResultSet rs = stmt.executeQuery("select * from book order by no");
			while (rs.next()) {
				//System.out.println("test");
				int no = rs.getInt(1);
				String title = rs.getString("title");
				String author = rs.getString(3);
				String isRent = rs.getString(4);
				Date regdate = rs.getDate(5);     //java.util로 임포트하기!
				Date editdate = rs.getDate(6);
				Book book = new Book(no, title, author, isRent, regdate, editdate);
				list.add(book);
			}
			DBUtil.closeConnection(conn, stmt, rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
}

