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
import Investing from "./component/Investing/Investing";
import Community from "./component/community/page/Community";
import "react-loading-skeleton/dist/skeleton.css";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
      staleTime: 1000 * 60,
      refetchOnWindowFocus: false,
    },
  },
});

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <ToastContainer />
        <Layout>
          <AnimatePresence mode="wait">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="login" element={<LoginSignup />} />
              <Route path="/invest" element={<InvestMain />} />
              <Route path="login/oauth2/code/kakao" element={<Oauth />} />
              <Route path="login/oauth2/code/naver" element={<Oauth />} />
              <Route path="/invest/:groupCode" element={<Investing />} />
              <Route path="/community" element={<Community />}></Route>
            </Routes>
          </AnimatePresence>
        </Layout>
      </BrowserRouter>
      <ReactQueryDevtools initialIsOpen={false} position="bottom-right" />
    </QueryClientProvider>
  );
}

export default App;
