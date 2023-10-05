import { Sidebar, Menu, MenuItem, SubMenu, MenuItemStyles, menuClasses } from "react-pro-sidebar";
import { TbDeviceDesktopAnalytics } from "react-icons/tb";
import { BsFilePerson } from "react-icons/bs";
import { GiSouthKorea } from "react-icons/gi";
import { AiOutlineFileSearch } from "react-icons/ai";
import { BsGlobeAmericas } from "react-icons/bs";
import { BsCurrencyBitcoin } from "react-icons/bs";
import { BsNewspaper } from "react-icons/bs";
import { LiaCoinsSolid } from "react-icons/lia";
import { INVESTING_TAB } from "../../constant/INVESTING_TAB";
import { useLocation } from "react-router-dom";

interface Props {
  activeTab: number;
  setActiveTab: React.Dispatch<React.SetStateAction<number>>;
}

const InvestSidebar = ({ activeTab, setActiveTab }: Props) => {
  const location = useLocation();
  const theme = {
    sidebar: {
      backgroundColor: "#ffffff",
      color: "#948d8d",
    },
    menu: {
      menuContent: "#ffffff",
      icon: "#948d8d",
      hover: {
        backgroundColor: "#ccffcc",
        color: "#21b621",
      },
      disabled: {
        color: "#99e699",
      },
    },
  };

  const menuItemStyles: MenuItemStyles = {
    root: {
      fontSize: "1rem",
      [`.${menuClasses.active}`]: {
        backgroundColor: theme.menu.hover.backgroundColor,
        color: theme.menu.hover.color,
      },
    },
    icon: {
      color: theme.menu.icon,
    },
    SubMenuExpandIcon: {
      color: "#b6b7b9",
    },
    subMenuContent: () => ({
      backgroundColor: theme.menu.menuContent,
    }),
    button: {
      "&:hover": {
        backgroundColor: theme.menu.hover.backgroundColor,
      },
    },
    label: ({ open }) => ({
      fontWeight: open ? "bold" : undefined,
    }),
  };

  return (
    <div className=" bg-white">
      <h5 className=" my-10 text-center font-regular text-dark">{location.state.title}</h5>
      <Sidebar
        backgroundColor={theme.sidebar.backgroundColor}
        rootStyles={{
          color: theme.sidebar.color,
          minWidth: "220px",
          width: "10%",
          minHeight: "92vh",
          backgroundColor: "#ffffff",
        }}
      >
        <Menu menuItemStyles={menuItemStyles}>
          <MenuItem
            icon={<TbDeviceDesktopAnalytics />}
            active={activeTab === INVESTING_TAB.INFO}
            onClick={() => setActiveTab(INVESTING_TAB.INFO)}
          >
            그룹 현황
          </MenuItem>
          <MenuItem
            icon={<BsFilePerson />}
            active={activeTab === INVESTING_TAB.MY_INFO}
            onClick={() => setActiveTab(INVESTING_TAB.MY_INFO)}
          >
            내 현황
          </MenuItem>
          <MenuItem
            icon={<BsNewspaper />}
            active={activeTab === INVESTING_TAB.NEWS}
            onClick={() => setActiveTab(INVESTING_TAB.NEWS)}
          >
            뉴스
          </MenuItem>
          <SubMenu label="투자" icon={<LiaCoinsSolid />}>
            <MenuItem
              icon={<GiSouthKorea />}
              active={activeTab === INVESTING_TAB.DOMESTIC}
              onClick={() => setActiveTab(INVESTING_TAB.DOMESTIC)}
            >
              국내 주식
            </MenuItem>
            <MenuItem
              icon={<BsGlobeAmericas />}
              active={activeTab === INVESTING_TAB.ABROAD}
              onClick={() => setActiveTab(INVESTING_TAB.ABROAD)}
            >
              해외 주식
            </MenuItem>
            <MenuItem
              icon={<BsCurrencyBitcoin />}
              active={activeTab === INVESTING_TAB.CRYPTO}
              onClick={() => setActiveTab(INVESTING_TAB.CRYPTO)}
            >
              가상 화폐
            </MenuItem>
            <MenuItem
              icon={<AiOutlineFileSearch />}
              active={activeTab === INVESTING_TAB.SEARCH}
              onClick={() => setActiveTab(INVESTING_TAB.SEARCH)}
            >
              통합 검색
            </MenuItem>
          </SubMenu>
        </Menu>
      </Sidebar>
    </div>
  );
};
export default InvestSidebar;
