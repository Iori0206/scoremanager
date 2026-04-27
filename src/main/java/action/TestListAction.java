package action; // ご自身のプロジェクトのパッケージ名に合わせてください

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListAction extends Action {
    @Override
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
       

        return "test_list.jsp";
    }
}