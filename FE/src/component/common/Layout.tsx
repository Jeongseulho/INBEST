import Header from "./Header";

interface LayoutProps {
  children: React.ReactNode;
}

export const Layout = ({ children }: LayoutProps) => {
  return (
    <main className=" bg-gray-200 h-full">
      <Header />
      {children}
    </main>
  );
};
