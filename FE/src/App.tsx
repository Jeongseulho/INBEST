import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./component/home/page/Home";
import LoginSignup from "./component/account/page/LoginSignup";
import { Layout } from "./component/common/Layout";
import InvestMain from "./component/invest-main/page/InvestMain";
function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginSignup />} />
          <Route path="/invest" element={<InvestMain />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
