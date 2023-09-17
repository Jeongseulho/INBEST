import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./component/home/page/Home";
import LoginSignup from "./component/account/page/LoginSignup";
import Oauth from "./component/account/page/Oauth";
import { Layout } from "./component/common/Layout";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import InvestMain from "./component/invest-main/page/InvestMain";
import { AnimatePresence } from "framer-motion";
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <AnimatePresence>
          <ToastContainer />
          <Layout>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="login" element={<LoginSignup />} />
              <Route path="/invest" element={<InvestMain />} />
              <Route path="login/oauth2/code/kakao" element={<Oauth />} />
              <Route path="login/oauth2/code/naver" element={<Oauth />} />
            </Routes>
          </Layout>
        </AnimatePresence>
      </BrowserRouter>
      <ReactQueryDevtools initialIsOpen={false} position="bottom-right" />
    </QueryClientProvider>
  );
}

export default App;
