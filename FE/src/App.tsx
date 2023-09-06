import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./component/home/Home";
import LoginSignup from "./component/account/page/LoginSignup";
import { Layout } from "./component/common/Layout";
function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginSignup />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
