import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./component/home/page/Home";
import LoginSignup from "./component/account/page/LoginSignup";
import Oauth from "./component/account/page/Oauth";
import { Layout } from "./component/common/Layout";
function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="login" element={<LoginSignup />} />
          <Route path="login/oauth" element={<Oauth />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
