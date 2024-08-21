{
  description = "Python shell";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/24.05";
  };

  outputs = { self, nixpkgs }:
    let
      forAllSystems = nixpkgs.lib.genAttrs [ "x86_64-linux" "aarch64-darwin" ];
      localPkgs = forAllSystems (system: import nixpkgs { system=system; overlays=serverOverlays; config = serverConfig; });
      serverPkgs = forAllSystems (system: import nixpkgs { system=system; overlays=serverOverlays; config = serverConfig; });
      serverOverlays = [
        
      ];
      serverConfig= {
        allowUnfree = true;
      };
      
      otherPackages = (ps: with ps; [ 
        bash
        unzip 
        gnumake
        jdk17
        maven
      ]);
    in
    {
      devShells = forAllSystems (system: {
        default = let pkgs = localPkgs.${system}; in pkgs.mkShellNoCC {
          packages = with pkgs; [
          ] ++ (otherPackages pkgs);
        };
      });
    };
}
