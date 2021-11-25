package webservices;

public class GameProxy implements webservices.Game {
  private String _endpoint = null;
  private webservices.Game game = null;
  
  public GameProxy() {
    _initGameProxy();
  }
  
  public GameProxy(String endpoint) {
    _endpoint = endpoint;
    _initGameProxy();
  }
  
  private void _initGameProxy() {
    try {
      game = (new webservices.GameServiceLocator()).getGame();
      if (game != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)game)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)game)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (game != null)
      ((javax.xml.rpc.Stub)game)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public webservices.Game getGame() {
    if (game == null)
      _initGameProxy();
    return game;
  }
  
  public boolean checkValid(model.Sudoku sudo) throws java.rmi.RemoteException{
    if (game == null)
      _initGameProxy();
    return game.checkValid(sudo);
  }
  
  public boolean checkWin(model.Sudoku sudo) throws java.rmi.RemoteException{
    if (game == null)
      _initGameProxy();
    return game.checkWin(sudo);
  }
  
  
}