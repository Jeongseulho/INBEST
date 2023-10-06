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
import CommunityList from "./component/community/organisms/CommunityList";
import CommunityCreate from "./component/community/organisms/CommunityCreate";
import CommunityDetail from "./component/community/organisms/CommunityDetail";
import Ranking from "./component/ranking/page/Ranking";
import "react-loading-skeleton/dist/skeleton.css";
import PersonalRanking from "./component/ranking/organisms/PersonalRanking";
import PersonalRankingSearch from "./component/ranking/organisms/PersonalRankingSearch";
import GroupRanking from "./component/ranking/organisms/GroupRanking";
import PrivateRoute from "./component/common/PrivateRoute";
import FinancialDictionary from "./component/financial-dictionary/page/FinancialDictionary";
import MemberProfile from "./component/account/page/MemberProfile";
import NotFound404 from "./component/common/NotFound404";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 2,
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

              <Route element={<PrivateRoute requireAuth={false} />}>
                <Route path="login" element={<LoginSignup />} />
              </Route>

              <Route element={<PrivateRoute requireAuth={true} />}>
                <Route path="/invest" element={<InvestMain />} />
              </Route>

              <Route element={<PrivateRoute requireAuth={true} />}>
                <Route path="profile/:memberSeq" element={<MemberProfile />} />
              </Route>

              <Route element={<PrivateRoute requireAuth={false} />}>
                <Route path="login/oauth2/code/kakao" element={<Oauth />} />
              </Route>

              <Route element={<PrivateRoute requireAuth={false} />}>
                <Route path="login/oauth2/code/naver" element={<Oauth />} />
              </Route>

              <Route element={<PrivateRoute requireAuth={true} />}>
                <Route path="community" element={<Community />}>
                  <Route index element={<CommunityList />} />
                  <Route path="create" element={<CommunityCreate />} />
                  <Route path="detail" element={<CommunityDetail />} />
                </Route>
              </Route>

              <Route element={<PrivateRoute requireAuth={true} />}>
                <Route path="/invest/:simulationSeq" element={<Investing />} />
              </Route>

              <Route element={<PrivateRoute requireAuth={true} />}>
                <Route path="ranking" element={<Ranking />}>
                  <Route index element={<PersonalRanking />} />
                  <Route path="group" element={<GroupRanking />} />
                  <Route path="group/:seq" element={<GroupRanking />} />
                  <Route path="search/:nickname" element={<PersonalRankingSearch />} />
                </Route>
              </Route>

              <Route element={<PrivateRoute requireAuth={true} />}>
                <Route path="financial-dictionary" element={<FinancialDictionary />} />
              </Route>
              <Route path="/*" element={<NotFound404 />} />
            </Routes>
          </AnimatePresence>
        </Layout>
      </BrowserRouter>
      <ReactQueryDevtools initialIsOpen={false} position="bottom-right" />
    </QueryClientProvider>
  );
}

export default App;
