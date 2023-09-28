import Header from "./Header";
import ChatIcon from "./ChatIcon";

interface Props {
  children: React.ReactNode;
}

export const Layout = ({ children }: Props) => {
  return (
    <div className="bg-gray-100 min-h-screen">
      <Header />
      <ChatIcon />
      {children}
    </div>
  );
};
