package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCEx {
//sqld에서 커밋확인!!!
	public static void main(String[] args) {
		JDBCEx ex = new JDBCEx();
//		ex.getList();
//		ex.insert();
//		ex.delete();
		ex.update();
	}
		
		
	public void getList() {
		Connection conn;
		
		// 데이터베이스 접근 시 필요한 정보
		// id, port, sid, 계정정보, 비밀번호
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";  //약속된거라 되도록 건드리지 않는게 좋음
		String id = "orauser";
		String pw = "1234";
		
		// 클래스 로딩 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from book order by no");
			List<Book> list = new ArrayList<>();
			while(rs.next()) {   //첫번쨰줄 외에 가져오려면 반복문 사용해야함
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String author = rs.getString(3);
				String isRent = rs.getString(4);
				Date regdate = rs.getDate(5);   //java.util로 임포트
				Date editDate = rs.getDate(6);
				
				Book book = new Book(no, title, author, isRent, regdate, editDate);
				list.add(book);
			}
			
			System.out.println(list);
			for(Book book : list) {
				System.out.println(book.toString());
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// > 드라이버 매니저로 커넥션 얻어오기(매개변수는 순서대로, 접속정보) 
		// > 쿼리실행 객체 생성하기(쿼리실행을 위해 statement객체준비) 
		// > 쿼리 실행 후 결과집합 받아오기
		
		
		
	}
	
	public void insert() {
		Connection conn;
		
		// 데이터베이스 접근 시 필요한 정보
		// id, port, sid, 계정정보, 비밀번호
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";  //약속된거라 되도록 건드리지 않는게 좋음
		String id = "orauser";
		String pw = "1234";
		
		// 클래스 로딩 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			Statement stmt = conn.createStatement();
			
			//executeUpdate : 몇 건이 처리되었는지 반환해줌
			int res = stmt.executeUpdate("insert into book values (10, '오라클을 다루는 기술', '박찬미', 'N', sysdate, null)");
			List<Book> list = new ArrayList<>();

				
			System.out.println(res + "건 처리되었습니다.");
			
			stmt.close();
			conn.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// > 드라이버 매니저로 커넥션 얻어오기(매개변수는 순서대로, 접속정보) 
		// > 쿼리실행 객체 생성하기(쿼리실행을 위해 statement객체준비) 
		// > 쿼리 실행 후 결과집합 받아오기
	}
	
	public void delete() {
				// 데이터베이스 접근 시 필요한 정보
				// id, port, sid, 계정정보, 비밀번호
				String url = "jdbc:oracle:thin:@localhost:1521:orcl";  //약속된거라 되도록 건드리지 않는게 좋음
				String id = "orauser";
				String pw = "1234";
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 커넥션을 얻어오기 위해 필요한 정보
			Connection conn = DriverManager.getConnection(url, id, pw);
			//자동커밋을 하지 않도록 처리
			System.out.println("오토커밋 - false 설정");
			conn.setAutoCommit(false);
			
			//쿼리실행 준비
			Statement stmt = conn.createStatement();
			// 몇건 처리되었는지 반환
			int res = stmt.executeUpdate("delete from book");
			System.out.println(res + "건 처리되었습니다.");
			conn.rollback();
			System.out.println("롤백 되었습니다");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update() {
		// 데이터베이스 접근 시 필요한 정보
		// id, port, sid, 계정정보, 비밀번호
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";  //약속된거라 되도록 건드리지 않는게 좋음
		String id = "orauser";
		String pw = "1234";
		
		try {
			// 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// DB연결 설정
			Connection conn = DriverManager.getConnection(url, id, pw);
			// 쿼리 실행 객체 생성
			Statement stmt = conn.createStatement();
			// 쿼리 실행
			int res = stmt.executeUpdate("update book set isrent = 'Y' where no = 10"); //이때 sqld가서 실행문 만들어줌
			// 결과 출력
			System.out.println(res + "건 처리되었습니다");
			
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println("jdbc 라이브러리를 확인해주세요");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void connectionTest() {
		// 데이터베이스 접근 시 필요한 정보
		// id, port, sid, 계정정보, 비밀번호
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";  //약속된거라 되도록 건드리지 않는게 좋음
		String id = "orauser";
		String pw = "1234";

		Connection conn;
		
		try {
			// 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 커넥션 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("커넥션 얻기 성공" + conn);
			// 쿼리 실행을 위해 필요한 객체 (.createStatement)  / java.sql 임포트
			Statement stmt = conn.createStatement();
			
			// 데이터 조회
			// executeQuery (결과 집합을 가져오기 위해 sqld에서 작성한 쿼리문장 입력(세미콜론제외하고 복붙))
			// ResultSet : 결과집합    
			ResultSet rs = stmt.executeQuery("select count(*) from book");
			rs.next();  //다음줄로 이동 후 값을 가져온다~  패치한다라고 함
			System.out.println("조회결과 : " + rs.getString(1));  // 첫번째 칼럼 가져옴/getString = 문자열로 반납 			
			
			// 자원반납   (제일 아랫것부터 닫아준다)
			rs.close();
			stmt.close();
			conn.close();
			// 데이터 생성, 수정, 삭제
			// stmt.executeUpdate("");
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
