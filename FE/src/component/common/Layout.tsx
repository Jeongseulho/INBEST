import Header from "./Header";

interface LayoutChildrenProps {
  children: React.ReactNode;
}

export const Layout = ({ children }: LayoutChildrenProps) => {
  return (
    <>
      <Header />
      {children}
    </>
  );
};
