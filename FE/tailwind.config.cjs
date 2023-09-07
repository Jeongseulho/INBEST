/**
 * @type {import('tailwindcss').Config}
 */
module.exports = {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        light: ["light"],
        regular: ["regular"],
        medium: ["medium"],
        semiBold: ["semibold"],
        bold: ["bold"],
        extraBold: ["extraBold"],
      },
      colors: {
        main: "#6CE061",
        light: "#6FFF8C",
        lima: "#ADF777",
        lime: "#C1E061",
      },
      textShadow: {
        sm: "0 1px 2px var(--tw-shadow-color)",
        DEFAULT: "0 2px 4px var(--tw-shadow-color)",
        lg: "0 8px 16px var(--tw-shadow-color)",
      },
    },
  },
  plugins: [],
};
