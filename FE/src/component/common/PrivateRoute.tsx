import { ReactElement, useState, useEffect } from "react";
import { Outlet } from "react-router-dom";
import LoginSignup from "../account/page/LoginSignup";
import userStore from "../../store/userStore";
import Home from "../home/page/Home";

interface PrivateRouteProps {
  children?: ReactElement; // Router.tsx에서 PrivateRoute가 감싸고 있는 Componet Element
  requireAuth: boolean; // true :인증을 반드시 해야하만 접속가능, false : 인증을 반드시 안해야만 접속 가능
}

export function PrivateRoute({ requireAuth }: PrivateRouteProps): React.ReactElement | null {
  const { accessToken } = userStore();
  const [routeEle, setRouteEle] = useState<ReactElement | null>(null);

  useEffect(() => {
    if (requireAuth) {
      setRouteEle(accessToken ? <Outlet /> : <LoginSignup />);
    } else {
      setRouteEle(accessToken ? <Home /> : <Outlet />);
    }
  }, [accessToken, requireAuth]);

  return routeEle;
}

export default PrivateRoute;
