import Header from "./Header";

interface LayoutProps {
  children: React.ReactNode;
}

export const Layout = ({ children }: LayoutProps) => {
  return (
    <div className=" bg-gray-50 min-h-screen">
      <Header />
      {children}
    </div>
  );
};
