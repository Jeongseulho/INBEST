import Header from "./Header";
import ChatIcon from "./ChatIcon";
import userStore from "../../store/userStore";

interface Props {
  children: React.ReactNode;
}

export const Layout = ({ children }: Props) => {
  const { accessToken } = userStore();
  return (
    <div className="bg-gray-100 min-h-screen">
      <Header />
      {accessToken && <ChatIcon />}
      {children}
    </div>
  );
};
